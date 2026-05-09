package com.gamehok.tournament.controller;


import com.gamehok.tournament.dto.ApiResponse;
import com.gamehok.tournament.dto.MatchResultRequest;
import com.gamehok.tournament.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMatch(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Success",
                matchService.getMatch(id)));
    }

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<?> getByTournament(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(ApiResponse.ok("Success",
                matchService.getMatchesByTournament(tournamentId)));
    }

    // Submit match result
    @PostMapping("/{id}/result")
    public ResponseEntity<?> submitResult(
            @PathVariable Long id,
            @Valid @RequestBody MatchResultRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Result submitted",
                matchService.submitResult(id, req)));
    }
}

