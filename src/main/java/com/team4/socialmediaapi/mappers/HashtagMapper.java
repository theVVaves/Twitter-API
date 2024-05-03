package com.team4.socialmediaapi.mappers;

import com.team4.socialmediaapi.dtos.HashtagDto;
import com.team4.socialmediaapi.entities.Hashtag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { TweetMapper.class })
public interface HashtagMapper {

    List<HashtagDto> entitiesToDtos(List<Hashtag> allHashtags);

    HashtagDto entityToDto(Hashtag hashtag);

    Hashtag dtoToEntity(HashtagDto dto);
}
