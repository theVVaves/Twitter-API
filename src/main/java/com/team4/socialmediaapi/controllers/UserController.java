package com.team4.socialmediaapi.controllers;

import com.team4.socialmediaapi.dtos.CredentialDto;
import com.team4.socialmediaapi.dtos.TweetResponseDto;
import com.team4.socialmediaapi.dtos.UserRequestDto;
import com.team4.socialmediaapi.dtos.UserResponseDto;
import com.team4.socialmediaapi.entities.Credentials;
import com.team4.socialmediaapi.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto){
        return userService.createUser(userRequestDto);
    }

    @GetMapping("/@{username}")
    public UserResponseDto getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @PatchMapping("/@{username}")
    public UserResponseDto updateUserProfile(@PathVariable String username, @RequestBody UserRequestDto userRequestDto){
        return userService.updateUserProfile(username, userRequestDto);
    }

    @DeleteMapping("/@{username}")
    public UserResponseDto deleteUser(@PathVariable String username, @RequestBody CredentialDto credentials){
        return userService.deleteUser(username, credentials);
    }

    // We want to make the user for the credentials provided follow the username given in the URL
    @PostMapping("/@{username}/follow")
    public void followUser(@PathVariable String username, @RequestBody Credentials credentials){
        userService.followUser(username, credentials);
    }

    @GetMapping("/@{username}/followers")
    public List<UserResponseDto> getAllFollowers(@PathVariable String username){
        return userService.getAllFollowers(username);
    }

    // We want to unfollow the username provided in the URL
    @PostMapping("/@{username}/unfollow")
    public void unfollowUser(@PathVariable String username, @RequestBody Credentials credentials){
        userService.unfollowUser(username, credentials);
    }

    @GetMapping("/@{username}/following")
    public List<UserResponseDto> getUserFollowingList(@PathVariable String username){
        return userService.getUserFollowingList(username);
    }

    // Get ALL not deleted tweets of the user and all the tweets of the people user is following - non deleted only
    @GetMapping("/@{username}/feed")
    public List<TweetResponseDto> getUserFeed(@PathVariable String username){
        return userService.getUserFeed(username);
    }

    @GetMapping("/@{username}/tweets")
    public List<TweetResponseDto> getAllUserTweets(@PathVariable String username){
        return userService.getAllUserTweets(username);
    }

    @GetMapping("/@{username}/mentions")
    public List<TweetResponseDto> getAllUserMentions(@PathVariable String username){
        return userService.getAllUserMentions(username);
    }

}
