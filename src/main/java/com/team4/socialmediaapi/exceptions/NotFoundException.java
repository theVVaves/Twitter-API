package com.team4.socialmediaapi.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 4230397501124709201L;

    private String message;

}
