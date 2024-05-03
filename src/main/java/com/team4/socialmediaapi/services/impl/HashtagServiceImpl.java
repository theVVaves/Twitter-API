package com.team4.socialmediaapi.services.impl;

import com.team4.socialmediaapi.dtos.HashtagDto;
import com.team4.socialmediaapi.dtos.TweetResponseDto;
import com.team4.socialmediaapi.entities.Hashtag;
import com.team4.socialmediaapi.exceptions.NotFoundException;
import com.team4.socialmediaapi.mappers.HashtagMapper;
import com.team4.socialmediaapi.mappers.TweetMapper;
import com.team4.socialmediaapi.repositories.HashtagRepository;
import org.springframework.stereotype.Service;

import com.team4.socialmediaapi.services.HashtagService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;
    private final TweetMapper tweetMapper;

    @Override
    public List<HashtagDto> getAllHashtags() {
        return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
    }

    @Override
    public List<TweetResponseDto> getTweetsOfTag(String label) {
        Optional<Hashtag> maybeHashtag = hashtagRepository.findByLabel(label);
        if (maybeHashtag.isEmpty()) {
            throw new NotFoundException("Tag not found");
        } else {
            return tweetMapper.entitiesToDtos(maybeHashtag.get().getTweets());
        }
    }
}
