package com.team4.socialmediaapi.mappers;

import com.team4.socialmediaapi.dtos.HashtagDto;
import com.team4.socialmediaapi.entities.Hashtag;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-02T21:37:26-0400",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240417-1011, environment: Java 22 (Oracle Corporation)"
)
@Component
public class HashtagMapperImpl implements HashtagMapper {

    @Override
    public List<HashtagDto> entitiesToDtos(List<Hashtag> allHashtags) {
        if ( allHashtags == null ) {
            return null;
        }

        List<HashtagDto> list = new ArrayList<HashtagDto>( allHashtags.size() );
        for ( Hashtag hashtag : allHashtags ) {
            list.add( entityToDto( hashtag ) );
        }

        return list;
    }

    @Override
    public HashtagDto entityToDto(Hashtag hashtag) {
        if ( hashtag == null ) {
            return null;
        }

        HashtagDto hashtagDto = new HashtagDto();

        hashtagDto.setFirstUsed( hashtag.getFirstUsed() );
        hashtagDto.setLabel( hashtag.getLabel() );
        hashtagDto.setLastUsed( hashtag.getLastUsed() );

        return hashtagDto;
    }

    @Override
    public Hashtag dtoToEntity(HashtagDto dto) {
        if ( dto == null ) {
            return null;
        }

        Hashtag hashtag = new Hashtag();

        hashtag.setFirstUsed( dto.getFirstUsed() );
        hashtag.setLabel( dto.getLabel() );
        hashtag.setLastUsed( dto.getLastUsed() );

        return hashtag;
    }
}
