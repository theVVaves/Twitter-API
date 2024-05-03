package com.team4.socialmediaapi.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {

    private CredentialDto credentials;

    private ProfileDto profile;

}
