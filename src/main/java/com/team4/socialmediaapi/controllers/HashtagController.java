package com.team4.socialmediaapi.controllers;

import com.team4.socialmediaapi.dtos.HashtagDto;
import com.team4.socialmediaapi.dtos.TweetResponseDto;
import com.team4.socialmediaapi.services.HashtagService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class HashtagController {
    private final HashtagService hashtagService;

    @GetMapping
    public List<HashtagDto> getAllHashTags() {
        return hashtagService.getAllHashtags();
    }

    @GetMapping("/{label}")
    @ResponseStatus(HttpStatus.OK)
    public List<TweetResponseDto> getTweetsOfTag(@PathVariable String label) {
        return hashtagService.getTweetsOfTag(label);
    }
}
