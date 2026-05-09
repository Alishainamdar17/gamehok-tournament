package com.gamehok.tournament.controller;


import com.gamehok.tournament.dto.ApiResponse;
import com.gamehok.tournament.model.User;
import com.gamehok.tournament.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        return ResponseEntity.ok(ApiResponse.ok("User created",
                userRepository.save(user)));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(ApiResponse.ok("Success",
                userRepository.findAll()));
    }
}