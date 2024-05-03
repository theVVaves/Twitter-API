package com.team4.socialmediaapi.services;

import java.util.List;

import com.team4.socialmediaapi.dtos.ContextDto;
import com.team4.socialmediaapi.dtos.CredentialDto;
import com.team4.socialmediaapi.dtos.HashtagDto;
import com.team4.socialmediaapi.dtos.TweetRequestDto;
import com.team4.socialmediaapi.dtos.TweetResponseDto;
import com.team4.socialmediaapi.dtos.UserResponseDto;
import com.team4.socialmediaapi.entities.Credentials;

public interface TweetService {
    ContextDto context(Long id);

    TweetResponseDto postTweet(TweetRequestDto tweetRequestDto);
    TweetResponseDto getTweetById(Long id);
    TweetResponseDto deleteTweet(Long id, Credentials credentials);
    TweetResponseDto replyToTweet(Long id, TweetRequestDto tweetRequestDto);
	TweetResponseDto repostTweet(Long id, CredentialDto credentialDto);

    List<TweetResponseDto> getAllTweets();
    List<TweetResponseDto> getTweetReplies(Long id);
	List<TweetResponseDto> getTweetReposts(Long id);

    List<HashtagDto> getTweetTags(Long id);

	List<UserResponseDto> getTweetMentions(Long id);
    List<UserResponseDto> getTweetLikes(Long id);

    void likeTweet(Long id, Credentials credentials);

}
