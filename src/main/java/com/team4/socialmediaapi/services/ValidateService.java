package com.team4.socialmediaapi.services;

public interface ValidateService {
    boolean validateUsernameExists(String username);

    boolean validateUsernameIsAvailable(String username);

    boolean hashtagExists(String label);
}
