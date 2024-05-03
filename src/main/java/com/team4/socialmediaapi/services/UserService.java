package com.team4.socialmediaapi.services;

import com.team4.socialmediaapi.dtos.CredentialDto;
import com.team4.socialmediaapi.dtos.TweetResponseDto;
import com.team4.socialmediaapi.dtos.UserRequestDto;
import com.team4.socialmediaapi.dtos.UserResponseDto;
import com.team4.socialmediaapi.entities.Credentials;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllUsers();

    List<UserResponseDto> getAllFollowers(String username);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto getUserByUsername(String username);

    UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto);

    UserResponseDto deleteUser(String username, CredentialDto credentials);

    List<UserResponseDto> getUserFollowingList(String username);

    List<TweetResponseDto> getUserFeed(String username);

    List<TweetResponseDto> getAllUserTweets(String username);

    List<TweetResponseDto> getAllUserMentions(String username);

    void followUser(String username, Credentials credentials);

    void unfollowUser(String username, Credentials credentials);

}