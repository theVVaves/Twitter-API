package com.team4.socialmediaapi.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.team4.socialmediaapi.dtos.ErrorDto;
import com.team4.socialmediaapi.exceptions.BadRequestException;
import com.team4.socialmediaapi.exceptions.NotAuthorizedException;
import com.team4.socialmediaapi.exceptions.NotFoundException;

@ControllerAdvice(basePackages = { "com.team4.socialmediaapi.controllers" })
@ResponseBody
public class SocialMediaControllerAdvice {

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleBadRequestException(BadRequestException badRequestException) {
      return new ErrorDto(badRequestException.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorDto handleNotFoundException(NotFoundException notFoundException) {
      return new ErrorDto(notFoundException.getMessage());
  }

  @ExceptionHandler(NotAuthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorDto handleNotAuthorizedException(NotAuthorizedException notAuthorizedException) {
      return new ErrorDto(notAuthorizedException.getMessage());
  }

}