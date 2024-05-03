package com.team4.socialmediaapi.mappers;

import com.team4.socialmediaapi.dtos.ProfileDto;
import com.team4.socialmediaapi.dtos.UserRequestDto;
import com.team4.socialmediaapi.dtos.UserResponseDto;
import com.team4.socialmediaapi.entities.Credentials;
import com.team4.socialmediaapi.entities.Profile;
import com.team4.socialmediaapi.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-02T21:37:26-0400",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240417-1011, environment: Java 22 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private CredentialsMapper credentialsMapper;

    @Override
    public UserResponseDto entityToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setUsername( userCredentialsUsername( user ) );
        userResponseDto.setCredentials( credentialsMapper.entityToDto( user.getCredentials() ) );
        userResponseDto.setJoined( user.getJoined() );
        userResponseDto.setProfile( profileToProfileDto( user.getProfile() ) );

        return userResponseDto;
    }

    @Override
    public User dtoToEntity(UserRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setCredentials( credentialsMapper.dtoToEntity( userRequestDto.getCredentials() ) );
        user.setProfile( profileMapper.dtoToEntity( userRequestDto.getProfile() ) );

        return user;
    }

    @Override
    public List<UserResponseDto> entitiesToDtos(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserResponseDto> list = new ArrayList<UserResponseDto>( users.size() );
        for ( User user : users ) {
            list.add( entityToDto( user ) );
        }

        return list;
    }

    private String userCredentialsUsername(User user) {
        if ( user == null ) {
            return null;
        }
        Credentials credentials = user.getCredentials();
        if ( credentials == null ) {
            return null;
        }
        String username = credentials.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    protected ProfileDto profileToProfileDto(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        ProfileDto profileDto = new ProfileDto();

        profileDto.setEmail( profile.getEmail() );
        profileDto.setFirstName( profile.getFirstName() );
        profileDto.setLastName( profile.getLastName() );
        profileDto.setPhone( profile.getPhone() );

        return profileDto;
    }
}
