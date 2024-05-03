package com.team4.socialmediaapi.entities;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Context {

	private Tweet target;
	
	private List<Tweet> before;
	
	private List<Tweet> after;
	
}
