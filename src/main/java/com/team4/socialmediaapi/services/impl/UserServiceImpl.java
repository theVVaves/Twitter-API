package com.team4.socialmediaapi.services.impl;

import com.team4.socialmediaapi.dtos.*;
import com.team4.socialmediaapi.entities.Credentials;
import com.team4.socialmediaapi.entities.Profile;
import com.team4.socialmediaapi.entities.Tweet;
import com.team4.socialmediaapi.entities.User;
import com.team4.socialmediaapi.exceptions.BadRequestException;
import com.team4.socialmediaapi.exceptions.NotFoundException;
import com.team4.socialmediaapi.mappers.CredentialsMapper;
import com.team4.socialmediaapi.mappers.ProfileMapper;
import com.team4.socialmediaapi.mappers.TweetMapper;
import com.team4.socialmediaapi.mappers.UserMapper;
import com.team4.socialmediaapi.repositories.UserRepository;
import com.team4.socialmediaapi.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final TweetMapper tweetMapper;

    private final CredentialsMapper credentialsMapper;

    private final ProfileMapper profileMapper;

    private static final String BAD_USER_CREATE_REQUEST_MSG = "Credentials cannot be null; username must be unique; Email cannot be null ";
    private static final String BAD_CRED_REQUEST_MSG = "There was an issue with your credentials ";
    private static final String BAD_PROF_REQUEST_MSG = "Your profile was not found ";
    private static final String USER_NOT_FOLLOWING_MSG = "You are not following this user ";
    private static final String USER_ALREADY_FOLLOWING_MSG = "You are already following this user ";
    private static final String USER_NOT_FOUND_MSG = "User not found in the database";

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRequestDto.getCredentials() == null || userRequestDto.getCredentials().getUsername() == null
                || userRequestDto.getCredentials().getPassword() == null || userRequestDto.getProfile() == null
                || userRequestDto.getProfile().getEmail() == null) {
            throw new BadRequestException(BAD_USER_CREATE_REQUEST_MSG);
        }
        User user;
        Credentials credentials = credentialsMapper.dtoToEntity(userRequestDto.getCredentials());
        Profile profile = profileMapper.dtoToEntity(userRequestDto.getProfile());
        user = userRepository.findByCredentialsUsernameAndCredentialsPassword(credentials.getUsername(),
                credentials.getPassword());
        if (user != null && user.isDeleted()) {
            user.setDeleted(false);
            user.setProfile(profile);
        } else {
            user = new User();
            user.setCredentials(credentials);
            user.setProfile(profile);
        }
        try {
            userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(BAD_USER_CREATE_REQUEST_MSG);
        }
        return userMapper.entityToDto(user);
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        User retrievedUser = findUser(username);

        return userMapper.entityToDto(retrievedUser);
    }

    @Override
    public UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto) {
        User foundUser = findUser(username);

        if (!userMapper.entityToDto(foundUser).getCredentials().equals(userRequestDto.getCredentials())) {
            throw new BadRequestException(BAD_CRED_REQUEST_MSG);
        }
        if (userRequestDto.getProfile() == null) {
            throw new BadRequestException(BAD_PROF_REQUEST_MSG);
        }

        ProfileDto requestProfile = userRequestDto.getProfile();
        Profile foundUserProfile = foundUser.getProfile();
        if (requestProfile.getFirstName() != null) {
            foundUserProfile.setFirstName(requestProfile.getFirstName());
        }
        if (requestProfile.getLastName() != null) {
            foundUserProfile.setLastName(requestProfile.getLastName());
        }
        if (requestProfile.getEmail() != null) {
            foundUserProfile.setEmail(requestProfile.getEmail());
        }
        if (requestProfile.getPhone() != null) {
            foundUserProfile.setPhone(requestProfile.getPhone());
        }

        foundUser.setProfile(foundUserProfile);
        userRepository.saveAndFlush(foundUser);

        return userMapper.entityToDto(foundUser);
    }

    @Override
    public UserResponseDto deleteUser(String username, CredentialDto credentials) {
        User retrievedUser = findUser(username);
        Credentials credential = new Credentials();
        credential.setPassword(credentials.getPassword());
        credential.setUsername(credentials.getUsername());

        if (!retrievedUser.getCredentials().equals(credential)) {
            throw new BadRequestException(BAD_CRED_REQUEST_MSG);
        }

        retrievedUser.setDeleted(true);
        userRepository.saveAndFlush(retrievedUser);

        return userMapper.entityToDto(retrievedUser);
    }

    @Override
    public void followUser(String username, Credentials credentials) {
        checkCredentials(credentials);

        User userToFollow = findUser(username);
        User follower = findUser(credentials.getUsername());
        List<User> following = follower.getFollowing();

        if (following.contains(userToFollow)) {
            throw new BadRequestException(USER_ALREADY_FOLLOWING_MSG);
        } else {
            following.add(userToFollow);
        }
        follower.setFollowing(following);
        userRepository.saveAndFlush(follower);
    }

    @Override
    public List<UserResponseDto> getAllFollowers(String username) {
        User user = findUser(username);
        if (user == null) {
            throw new NotFoundException(USER_NOT_FOUND_MSG + username);
        }
        return userMapper.entitiesToDtos(user.getFollowers());
    }

    @Override
    public void unfollowUser(String username, Credentials credentials) {
        checkCredentials(credentials);
        User userToUnfollow = findUser(username);
        User unfollower = findUser(credentials.getUsername());

        List<User> following = unfollower.getFollowing();
        if (following.contains(userToUnfollow)) {
            following.remove(userToUnfollow);
        } else {
            throw new BadRequestException(USER_NOT_FOLLOWING_MSG);
        }

        unfollower.setFollowing(following);

        userRepository.saveAndFlush(unfollower);
    }

    @Override
    public List<UserResponseDto> getUserFollowingList(String username) {
        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);

        if (optionalUser.isEmpty()){
            throw new NotFoundException(USER_NOT_FOUND_MSG);
        }

        User retrievedUser = optionalUser.get();
        List<User> userFollowingList = retrievedUser.getFollowing();
        return userMapper.entitiesToDtos(userFollowingList);
    }

    /*
     * GET users/@{username}/feed
     * Retrieves all (non-deleted) tweets authored by the user with the given
     * username,
     * as well as all (non-deleted) tweets authored by users the given user is
     * following.
     * This includes simple tweets, reposts, and replies. The tweets should appear
     * in
     * reverse-chronological order. If no active user with that username exists
     * (deleted or never created), an error should be sent in lieu of a response.
     */
    @Override
    public List<TweetResponseDto> getUserFeed(String username) {
        User user = findUser(username);
        List<Tweet> feed = user.getTweets();
        for (User follower : user.getFollowers()) {
            feed.addAll(follower.getTweets());
        }
        Collections.sort(feed);
        Collections.reverse(feed);
        return tweetMapper.entitiesToDtos(feed);
    }

    // would like to do these with streams but running into a type error
    @Override
    public List<TweetResponseDto> getAllUserTweets(String username) {
        User user = findUser(username);
        List<Tweet> tweets = user.getTweets();
        Collections.sort(tweets);
        Collections.reverse(tweets);
        return tweetMapper.entitiesToDtos(tweets);
    }

    @Override
    public List<TweetResponseDto> getAllUserMentions(String username) {
        User user = findUser(username);
        List<Tweet> mentions = user.getMentions();
        Collections.sort(mentions);
        Collections.reverse(mentions);
        return tweetMapper.entitiesToDtos(mentions);
    }

    // helper methods
    private User findUser(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND_MSG + username);
        }

        return optionalUser.get();

    }

    private void checkCredentials(Credentials credentials) {
        User user = findUser(credentials.getUsername());
        if (!user.getCredentials().equals(credentials)) {
            throw new BadRequestException(BAD_CRED_REQUEST_MSG);
        }
    }
}