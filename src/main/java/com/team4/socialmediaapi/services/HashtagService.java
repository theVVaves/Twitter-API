package com.team4.socialmediaapi.services;

import com.team4.socialmediaapi.dtos.HashtagDto;
import com.team4.socialmediaapi.dtos.TweetResponseDto;

import java.util.List;

public interface HashtagService {

    List<HashtagDto> getAllHashtags();

    List<TweetResponseDto> getTweetsOfTag(String label);
}
