package com.team4.socialmediaapi.mappers;

import com.team4.socialmediaapi.dtos.CredentialDto;
import com.team4.socialmediaapi.entities.Credentials;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-02T21:37:26-0400",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240417-1011, environment: Java 22 (Oracle Corporation)"
)
@Component
public class CredentialsMapperImpl implements CredentialsMapper {

    @Override
    public Credentials dtoToEntity(CredentialDto credentialDto) {
        if ( credentialDto == null ) {
            return null;
        }

        Credentials credentials = new Credentials();

        credentials.setPassword( credentialDto.getPassword() );
        credentials.setUsername( credentialDto.getUsername() );

        return credentials;
    }

    @Override
    public CredentialDto entityToDto(Credentials originalCredentials) {
        if ( originalCredentials == null ) {
            return null;
        }

        CredentialDto credentialDto = new CredentialDto();

        credentialDto.setPassword( originalCredentials.getPassword() );
        credentialDto.setUsername( originalCredentials.getUsername() );

        return credentialDto;
    }
}
