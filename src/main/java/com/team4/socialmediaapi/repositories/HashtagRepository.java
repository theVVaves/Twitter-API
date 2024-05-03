package com.team4.socialmediaapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.team4.socialmediaapi.entities.Hashtag;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long>{
    @NonNull
    Optional<Hashtag> findById(@NonNull Long tagId);

    Optional<Hashtag> findByLabel(String label);
}
