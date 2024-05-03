package com.team4.socialmediaapi.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetRequestDto {
    private CredentialDto credentials;

	private String content;
}
