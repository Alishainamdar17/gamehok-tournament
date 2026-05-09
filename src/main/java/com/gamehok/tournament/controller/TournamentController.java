package com.gamehok.tournament.controller;


import com.gamehok.tournament.dto.ApiResponse;
import com.gamehok.tournament.dto.TournamentRequest;
import com.gamehok.tournament.service.TournamentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;

    // Create tournament
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TournamentRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Tournament created",
                tournamentService.createTournament(req)));
    }

    // Get all tournaments
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(ApiResponse.ok("Success",
                tournamentService.getAllTournaments()));
    }

    // Get tournament by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Success",
                tournamentService.getTournament(id)));
    }

    // Register team in tournament
    @PostMapping("/{id}/register/{teamId}")
    public ResponseEntity<?> register(@PathVariable Long id, @PathVariable Long teamId) {
        return ResponseEntity.ok(ApiResponse.ok("Team registered",
                tournamentService.registerTeam(id, teamId)));
    }

    // Start tournament & generate bracket
    @PostMapping("/{id}/start")
    public ResponseEntity<?> start(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Tournament started! Bracket generated.",
                tournamentService.startTournament(id)));
    }

    // Get bracket
    @GetMapping("/{id}/bracket")
    public ResponseEntity<?> bracket(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Bracket fetched",
                tournamentService.getBracket(id)));
    }
}

