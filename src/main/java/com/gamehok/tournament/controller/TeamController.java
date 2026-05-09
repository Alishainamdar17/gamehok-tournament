package com.gamehok.tournament.controller;


import com.gamehok.tournament.dto.ApiResponse;
import com.gamehok.tournament.dto.TeamRequest;
import com.gamehok.tournament.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TeamRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Team created",
                teamService.createTeam(req)));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(ApiResponse.ok("Success",
                teamService.getAllTeams()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Success",
                teamService.getTeam(id)));
    }
}
