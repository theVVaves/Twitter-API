package com.team4.socialmediaapi.repositories;

import com.team4.socialmediaapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.team4.socialmediaapi.entities.Tweet;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    @NonNull
    Optional<Tweet> findById(@NonNull Long id);

    Optional<Tweet> findByIdAndDeletedFalse(Long id);

    List<Tweet> findAllByAuthor(User user);

    List<Tweet> findAllByDeletedFalse();

}
