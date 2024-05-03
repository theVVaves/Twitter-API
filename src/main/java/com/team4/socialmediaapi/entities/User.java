package com.team4.socialmediaapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@NoArgsConstructor
@Data
@Table(name = "user_table")
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
		
	@Embedded
	private Profile profile;
	
	@Embedded
	private Credentials credentials;
	
	@CreationTimestamp
	private Timestamp joined = Timestamp.valueOf(LocalDateTime.now());
	
	private boolean deleted;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Tweet> tweets;
	
	@ManyToMany(mappedBy = "following", cascade = CascadeType.ALL)
	private List<User> followers;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<User> following;
	
	@ManyToMany(mappedBy = "likes", cascade = CascadeType.ALL)
	private List<Tweet> likedTweets;
	
	@ManyToMany(mappedBy = "mentionedUsers", cascade = CascadeType.ALL)
	private List<Tweet> mentions;
	
}