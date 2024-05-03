package com.team4.socialmediaapi.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class Tweet implements Comparable<Tweet> {
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;
	
	private Timestamp posted = Timestamp.valueOf(LocalDateTime.now());
	
	private String content;
	
	@ManyToOne
//	@JoinColumn(name = "inReplyTo_id")
	private Tweet inReplyTo;
	
	@OneToMany(mappedBy = "inReplyTo", cascade = CascadeType.ALL)
	private List<Tweet> replies;
	
	@ManyToOne
//	@JoinColumn(name = "repostOf_id")
	private Tweet repostOf;
	
	@OneToMany(mappedBy = "repostOf", cascade = CascadeType.ALL)
	private List<Tweet> reposts;
	
	private boolean deleted;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<User> likes;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<User> mentionedUsers;
	
    @ManyToMany(cascade= CascadeType.MERGE)
    @JoinTable(
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
            )
    private List<Hashtag> hashtags = new ArrayList<>();

	@Override
	public int compareTo(Tweet t) {
		if (getPosted() == null || t.getPosted() == null) {
			return 0;
		}
		return getPosted().compareTo(t.getPosted());
	}
}
