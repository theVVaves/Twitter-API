package com.team4.socialmediaapi.mappers;

import com.team4.socialmediaapi.dtos.CredentialDto;
import com.team4.socialmediaapi.entities.Credentials;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {
    Credentials dtoToEntity(CredentialDto credentialDto);
    CredentialDto entityToDto(Credentials originalCredentials);
}
