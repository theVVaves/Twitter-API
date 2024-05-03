package com.team4.socialmediaapi.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.team4.socialmediaapi.dtos.*;
import com.team4.socialmediaapi.entities.*;
import com.team4.socialmediaapi.exceptions.*;
import com.team4.socialmediaapi.mappers.*;
import com.team4.socialmediaapi.repositories.*;
import com.team4.socialmediaapi.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;

    private final HashtagMapper hashtagMapper;
    private final HashtagRepository hashtagRepository;

    private final CredentialsMapper credentialsMapper;

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    private static final String TWEET_NOT_FOUND_MSG = "Tweet not found with ID: ";
    private static final String BAD_REQUEST_MSG = "Error while processing the request ";
    private static final String TAGS_NOT_FOUND_MSG = "Tags not found with ID: ";
    private static final String USER_NOT_FOUND_MSG = "User not found in the database";

    @Override
    public ContextDto context(Long id) {
        Tweet targetTweet = findTweet(id);
        ContextDto context = new ContextDto();
        List<TweetResponseDto> listBefore = new ArrayList<>();
        Tweet currentTweet = targetTweet;

        context.setTarget(tweetMapper.entityToDto(targetTweet));
        context.setAfter(tweetMapper.entitiesToDtos(targetTweet.getReplies()));

        while (currentTweet.getInReplyTo() != null) {
            listBefore.add(tweetMapper.entityToDto(currentTweet.getInReplyTo()));
            currentTweet = currentTweet.getInReplyTo();
        }

        context.setBefore(listBefore);

        return context;
    }

    @Override
    public TweetResponseDto getTweetById(Long id) {
        Tweet tweet = findTweet(id);
        return tweetMapper.entityToDto(tweet);
    }

    @Override
    public List<TweetResponseDto> getAllTweets() {
        return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalse());
    }

    @Override
    public TweetResponseDto postTweet(TweetRequestDto tweetRequestDto) {
        if (tweetRequestDto.getCredentials() == null || tweetRequestDto.getContent() == null) {
            throw new BadRequestException(BAD_REQUEST_MSG);
        }

        Credentials credentials = credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials());
        Optional<User> maybeUser = userRepository.findByCredentials(credentials);

        try {
            if (maybeUser.isPresent()) {
                User author = maybeUser.get();

                Tweet tweetToSave = tweetMapper.dtoToEntity(tweetRequestDto);
                tweetToSave.setAuthor(author);
                List<User> mentionedUsers = findMentions(tweetToSave.getContent());
                tweetToSave.setMentionedUsers(mentionedUsers);

                List<Hashtag> potentialtags = findTags(tweetToSave.getContent());
                tweetToSave.setHashtags(potentialtags);

                Tweet savedTweet = tweetRepository.saveAndFlush(tweetToSave);
                return tweetMapper.entityToDto(savedTweet);
            } else {
                throw new NotFoundException(USER_NOT_FOUND_MSG);
            }
        } catch (Exception e) {
            throw new BadRequestException(BAD_REQUEST_MSG);
        }
    }

    @Override
    public TweetResponseDto repostTweet(Long id, CredentialDto credentialDto) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        Credentials credentials = credentialsMapper.dtoToEntity(credentialDto);
        Optional<User> optionalUser = userRepository.findByCredentials(credentials);

        if (!optionalTweet.isPresent() || optionalTweet.get().isDeleted()) {
            throw new NotFoundException(TWEET_NOT_FOUND_MSG);
        }
        try {
            User user = optionalUser.get();
            Tweet replyToTweet = optionalTweet.get();
            if (user.getTweets().contains(replyToTweet)) {
                throw new BadRequestException("User has already reposted this tweet");
            }
            Tweet newTweetReplying = new Tweet();

            newTweetReplying.setAuthor(optionalUser.get());
            newTweetReplying.setRepostOf(replyToTweet);

            return tweetMapper.entityToDto(tweetRepository.saveAndFlush(newTweetReplying));

        } catch (Exception e) {
            throw new BadRequestException(BAD_REQUEST_MSG);
        }
    }

    public void likeTweet(Long id, Credentials credentials) {
        Tweet tweetToLike = findTweet(id);
        checkCredentials(credentials);

        List<User> tweetLikes = tweetToLike.getLikes();
        User userToAdd = findUser(credentials.getUsername());

        if (!tweetLikes.contains(userToAdd)) {
            tweetLikes.add(userToAdd);
        }

        tweetRepository.saveAndFlush(tweetToLike);
    }

    public TweetResponseDto replyToTweet(Long id, TweetRequestDto tweetRequestDto) {
        Tweet tweetToReplyTo = findTweet(id);

        validateTweetRequest(tweetRequestDto);

        User user = findUser(tweetRequestDto.getCredentials().getUsername());
        Tweet replyTweet = tweetMapper.dtoToEntity(tweetRequestDto);
        List<Tweet> tweetReplies = tweetToReplyTo.getReplies();

        replyTweet.setAuthor(user);
        replyTweet.setInReplyTo(tweetToReplyTo);
        tweetReplies.add(replyTweet);

        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(replyTweet));

    }

    public TweetResponseDto deleteTweet(Long id, Credentials credentials) {
        Tweet tweetToDelete = findTweet(id);
        checkCredentials(credentials);

        tweetToDelete.setDeleted(true);
        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweetToDelete));
    }

    @Override
    public List<TweetResponseDto> getTweetReplies(Long id) {
        Tweet tweetToFind = findTweet(id);
        List<Tweet> repliesToTweet = tweetToFind.getReplies();

        return tweetMapper.entitiesToDtos(repliesToTweet);
    }

    @Override
    public List<TweetResponseDto> getTweetReposts(Long id) {
        Tweet tweetToFind = findTweet(id);
        List<Tweet> repostsToTweet = tweetToFind.getReposts();

        return tweetMapper.entitiesToDtos(repostsToTweet);
    }

    @Override
    public List<HashtagDto> getTweetTags(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);

        try {
            if (optionalTweet.isPresent()) {
                Tweet selectedTweet = optionalTweet.get();
                List<Hashtag> associatedTags = selectedTweet.getHashtags();
                return hashtagMapper.entitiesToDtos(associatedTags);
            } else {
                throw new NotFoundException(TAGS_NOT_FOUND_MSG + id);
            }
        } catch (Exception e) {
            throw new BadRequestException(BAD_REQUEST_MSG);
        }
    }

    @Override
    public List<UserResponseDto> getTweetLikes(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);

        try {
            if (optionalTweet.isPresent()) {
                Tweet selectedTweet = optionalTweet.get();

                List<User> userLikes = new ArrayList<>();

                for (User u : selectedTweet.getLikes()) {

                    if (!u.isDeleted()) {
                        userLikes.add(u);
                    }
                }

                return userMapper.entitiesToDtos(userLikes);
            } else {
                throw new NotFoundException(TAGS_NOT_FOUND_MSG + id);
            }
        } catch (Exception e) {
            throw new BadRequestException(BAD_REQUEST_MSG);
        }
    }

    @Override
    public List<UserResponseDto> getTweetMentions(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);

        try {
            if (optionalTweet.isPresent()) {
                Tweet selectedTweet = optionalTweet.get();
                List<User> mentionedUsers = selectedTweet.getMentionedUsers();
                return userMapper.entitiesToDtos(mentionedUsers);
            } else {
                throw new NotFoundException(TWEET_NOT_FOUND_MSG + id);
            }
        } catch (Exception e) {
            throw new BadRequestException(BAD_REQUEST_MSG);
        }
    }


    // helper methods
    private void validateTweetRequest(TweetRequestDto tweetRequestDto) {
        if (tweetRequestDto.getCredentials() == null || tweetRequestDto.getCredentials().getUsername() == null
                || tweetRequestDto.getCredentials().getPassword() == null || tweetRequestDto.getContent() == null) {
            throw new BadRequestException("Something you entered is null, try again.");
        }
    }

    private Tweet findTweet(Long id) {
        Optional<Tweet> tweet = tweetRepository.findByIdAndDeletedFalse(id);
        if (tweet.isEmpty()) {
            throw new NotFoundException("Tweet with Id of " + id + " was not found");
        }
        return tweet.get();
    }

    private User findUser(String username) {
        Optional<User> maybeUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (maybeUser.isEmpty()) {
            throw new NotFoundException("No user was found with the username: " + username);
        }
        return maybeUser.get();
    }

    private void checkCredentials(Credentials credentials) {
        User user = findUser(credentials.getUsername());

        if (!user.getCredentials().equals(credentials)) {
            throw new NotAuthorizedException("Invalid credentials: " + credentials);
        }
    }

    public List<User> findMentions(String tweetContent) {
        List<User> mentions = new ArrayList<>();

        Pattern mentionsPattern = Pattern.compile("@[\\w_]+");

        Matcher matcher = mentionsPattern.matcher(tweetContent);

        while (matcher.find()) {
            String username = matcher.group().substring(1);
            User user = userRepository.findByCredentialsUsername(username);

            if (user != null) {
                mentions.add(user);

                System.out.print(user.getCredentials().getUsername());
            }
        }
        return mentions;
    }

    public List<Hashtag> findTags(String tweetContent) {
        List<Hashtag> tags = new ArrayList<>();

        Pattern tagPattern = Pattern.compile("#[\\w_]+");
        Matcher matcher = tagPattern.matcher(tweetContent);

        while (matcher.find()) {
            String tagLabel = matcher.group().substring(1);
            Optional<Hashtag> tag = hashtagRepository.findByLabel(tagLabel);
            if (tag.isPresent()) {
                tags.add(tag.get());
            } else {
                Hashtag newtag = new Hashtag();
                newtag.setLabel(tagLabel);
                hashtagRepository.save(newtag);
                tags.add(newtag);
            }
        }
        return tags;
    }
}