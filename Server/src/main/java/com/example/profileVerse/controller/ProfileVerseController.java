package com.example.profileVerse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileVerseController {

	@GetMapping("/")
    public String home() {
        return "Welcome to ProfileVerse!";
	}

}


