package com.team4.socialmediaapi.controllers;

import java.util.List;

import com.team4.socialmediaapi.dtos.*;
import com.team4.socialmediaapi.entities.Credentials;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.team4.socialmediaapi.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {

	private final TweetService tweetService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<TweetResponseDto> getAllTweets() {
		return tweetService.getAllTweets();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public TweetResponseDto getTweetById(@PathVariable Long id) {
		return tweetService.getTweetById(id);
	}

	@GetMapping("/{id}/context")
	public ContextDto findContext(@PathVariable Long id) {
		return tweetService.context(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TweetResponseDto postTweet(@RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.postTweet(tweetRequestDto);
	}

		@PostMapping("/{id}/like")
	@ResponseStatus(HttpStatus.CREATED)
	public void likeTweet(@PathVariable Long id, @RequestBody Credentials credentials) {
		tweetService.likeTweet(id, credentials);
	}

	@PostMapping("/{id}/reply")
	@ResponseStatus(HttpStatus.CREATED)
	public TweetResponseDto tweetReply(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.replyToTweet(id, tweetRequestDto);
	}

	@DeleteMapping("/{id}")
	public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody Credentials credentials) {
		return tweetService.deleteTweet(id, credentials);
	}

	@PostMapping("/{id}/repost")
	public TweetResponseDto respostTweet(@PathVariable Long id, @RequestBody CredentialDto credentialsDto) {
		return tweetService.repostTweet(id, credentialsDto);
	}

	@GetMapping("/{id}/likes")
	public List<UserResponseDto> getTweetLikes(@PathVariable Long id) {
		return tweetService.getTweetLikes(id);
	}

	@GetMapping("/{id}/tags")
	@ResponseStatus(HttpStatus.OK)
	public List<HashtagDto> getTags(@PathVariable Long id) {
		return tweetService.getTweetTags(id);
	}

	@GetMapping("/{id}/mentions")
	@ResponseStatus(HttpStatus.OK)
	public List<UserResponseDto> getMentions(@PathVariable Long id){
		return tweetService.getTweetMentions(id);
	}

	@GetMapping("/{id}/reposts")
	@ResponseStatus(HttpStatus.OK)
	public List<TweetResponseDto> getTweetReposts(@PathVariable Long id) {
		return tweetService.getTweetReposts(id);
	}

	@GetMapping("/{id}/replies")
	@ResponseStatus(HttpStatus.OK)
	public List<TweetResponseDto> getTweetReplies(@PathVariable Long id) {
		return tweetService.getTweetReplies(id);
	}


}
