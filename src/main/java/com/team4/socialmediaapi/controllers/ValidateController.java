package com.team4.socialmediaapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.team4.socialmediaapi.services.ValidateService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {
    private final ValidateService validateService;

    @GetMapping("/username/exists/@{username}")
    public boolean validateUsernameExists(@PathVariable String username) {
        return validateService.validateUsernameExists(username);
    }

    @GetMapping("/username/available/@{username}")
    public boolean validateUsernameIsAvailable(@PathVariable String username) {
        return validateService.validateUsernameIsAvailable(username);
    }

    @GetMapping("/tag/exists/{label}")
    public boolean hashtagExists(@PathVariable String label) {
        return validateService.hashtagExists(label);
    }

}
