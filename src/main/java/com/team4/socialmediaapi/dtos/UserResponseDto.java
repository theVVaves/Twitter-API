package com.team4.socialmediaapi.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {

    private String username;

    private CredentialDto credentials;

    private ProfileDto profile;

    private Timestamp joined;

}
