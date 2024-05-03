package com.team4.socialmediaapi.mappers;

import com.team4.socialmediaapi.dtos.ProfileDto;
import com.team4.socialmediaapi.entities.Profile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-02T21:37:26-0400",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240417-1011, environment: Java 22 (Oracle Corporation)"
)
@Component
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public Profile dtoToEntity(ProfileDto profileDto) {
        if ( profileDto == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setEmail( profileDto.getEmail() );
        profile.setFirstName( profileDto.getFirstName() );
        profile.setLastName( profileDto.getLastName() );
        profile.setPhone( profileDto.getPhone() );

        return profile;
    }
}
