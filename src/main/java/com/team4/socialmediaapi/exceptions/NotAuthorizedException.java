package com.team4.socialmediaapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5988538936840313491L;
	private String message;

}
