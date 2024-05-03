package com.team4.socialmediaapi.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.team4.socialmediaapi.entities.Hashtag;
import com.team4.socialmediaapi.entities.User;
import com.team4.socialmediaapi.repositories.HashtagRepository;
import com.team4.socialmediaapi.repositories.UserRepository;
import com.team4.socialmediaapi.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;

    @Override
    public boolean validateUsernameExists(String username) {
        Optional<User> maybeUser = userRepository.findByCredentials_Username(username);
        return !(maybeUser.isEmpty());
    }

    @Override
    public boolean validateUsernameIsAvailable(String username) {
        Optional<User> maybeUser = userRepository.findByCredentials_Username(username);
        return maybeUser.isEmpty();
    }

    @Override
    public boolean hashtagExists(String label) {
		Optional<Hashtag> maybeHashtag = hashtagRepository.findByLabel(label);
		return !(maybeHashtag.isEmpty());
    }
}