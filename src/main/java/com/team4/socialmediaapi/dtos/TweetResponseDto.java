package com.team4.socialmediaapi.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetResponseDto {
    private Long id;
    private Timestamp posted;
    private UserResponseDto author;
    private TweetRequestDto repostOf;
    private TweetResponseDto inReplyTo;
    private String content;
}
