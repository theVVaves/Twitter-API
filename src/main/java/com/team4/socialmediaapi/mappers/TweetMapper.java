package com.team4.socialmediaapi.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;

import com.team4.socialmediaapi.dtos.TweetRequestDto;
import com.team4.socialmediaapi.dtos.TweetResponseDto;
import com.team4.socialmediaapi.entities.Tweet;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TweetMapper {
    TweetResponseDto entityToDto(Tweet tweet);
    Tweet dtoToEntity(TweetRequestDto dto);
    TweetRequestDto entityToRequestDto(Tweet tweet);
    Tweet responseDtoToEntity(TweetResponseDto tweetResponseDto);

    List<TweetResponseDto> setEntitiesToDtos(Set<Tweet> entities);
    List<TweetResponseDto> entitiesToDtos(List<Tweet> tweet);

}
