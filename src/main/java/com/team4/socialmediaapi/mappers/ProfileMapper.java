package com.team4.socialmediaapi.mappers;

import org.mapstruct.Mapper;

import com.team4.socialmediaapi.dtos.ProfileDto;
import com.team4.socialmediaapi.entities.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
        Profile dtoToEntity(ProfileDto profileDto);


}
