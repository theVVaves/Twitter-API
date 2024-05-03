package com.team4.socialmediaapi.mappers;

import com.team4.socialmediaapi.dtos.CredentialDto;
import com.team4.socialmediaapi.dtos.ProfileDto;
import com.team4.socialmediaapi.dtos.TweetRequestDto;
import com.team4.socialmediaapi.dtos.TweetResponseDto;
import com.team4.socialmediaapi.dtos.UserResponseDto;
import com.team4.socialmediaapi.entities.Credentials;
import com.team4.socialmediaapi.entities.Profile;
import com.team4.socialmediaapi.entities.Tweet;
import com.team4.socialmediaapi.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-02T21:37:26-0400",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240417-1011, environment: Java 22 (Oracle Corporation)"
)
@Component
public class TweetMapperImpl implements TweetMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public TweetResponseDto entityToDto(Tweet tweet) {
        if ( tweet == null ) {
            return null;
        }

        TweetResponseDto tweetResponseDto = new TweetResponseDto();

        tweetResponseDto.setAuthor( userMapper.entityToDto( tweet.getAuthor() ) );
        tweetResponseDto.setContent( tweet.getContent() );
        tweetResponseDto.setId( tweet.getId() );
        tweetResponseDto.setInReplyTo( entityToDto( tweet.getInReplyTo() ) );
        tweetResponseDto.setPosted( tweet.getPosted() );
        tweetResponseDto.setRepostOf( entityToRequestDto( tweet.getRepostOf() ) );

        return tweetResponseDto;
    }

    @Override
    public Tweet dtoToEntity(TweetRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Tweet tweet = new Tweet();

        tweet.setContent( dto.getContent() );

        return tweet;
    }

    @Override
    public TweetRequestDto entityToRequestDto(Tweet tweet) {
        if ( tweet == null ) {
            return null;
        }

        TweetRequestDto tweetRequestDto = new TweetRequestDto();

        tweetRequestDto.setContent( tweet.getContent() );

        return tweetRequestDto;
    }

    @Override
    public Tweet responseDtoToEntity(TweetResponseDto tweetResponseDto) {
        if ( tweetResponseDto == null ) {
            return null;
        }

        Tweet tweet = new Tweet();

        tweet.setAuthor( userResponseDtoToUser( tweetResponseDto.getAuthor() ) );
        tweet.setContent( tweetResponseDto.getContent() );
        tweet.setId( tweetResponseDto.getId() );
        tweet.setInReplyTo( responseDtoToEntity( tweetResponseDto.getInReplyTo() ) );
        tweet.setPosted( tweetResponseDto.getPosted() );
        tweet.setRepostOf( dtoToEntity( tweetResponseDto.getRepostOf() ) );

        return tweet;
    }

    @Override
    public List<TweetResponseDto> setEntitiesToDtos(Set<Tweet> entities) {
        if ( entities == null ) {
            return null;
        }

        List<TweetResponseDto> list = new ArrayList<TweetResponseDto>( entities.size() );
        for ( Tweet tweet : entities ) {
            list.add( entityToDto( tweet ) );
        }

        return list;
    }

    @Override
    public List<TweetResponseDto> entitiesToDtos(List<Tweet> tweet) {
        if ( tweet == null ) {
            return null;
        }

        List<TweetResponseDto> list = new ArrayList<TweetResponseDto>( tweet.size() );
        for ( Tweet tweet1 : tweet ) {
            list.add( entityToDto( tweet1 ) );
        }

        return list;
    }

    protected Credentials credentialDtoToCredentials(CredentialDto credentialDto) {
        if ( credentialDto == null ) {
            return null;
        }

        Credentials credentials = new Credentials();

        credentials.setPassword( credentialDto.getPassword() );
        credentials.setUsername( credentialDto.getUsername() );

        return credentials;
    }

    protected Profile profileDtoToProfile(ProfileDto profileDto) {
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

    protected User userResponseDtoToUser(UserResponseDto userResponseDto) {
        if ( userResponseDto == null ) {
            return null;
        }

        User user = new User();

        user.setCredentials( credentialDtoToCredentials( userResponseDto.getCredentials() ) );
        user.setJoined( userResponseDto.getJoined() );
        user.setProfile( profileDtoToProfile( userResponseDto.getProfile() ) );

        return user;
    }
}
