package com.gamehok.tournament.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class MatchResultRequest {
    @NotNull(message = "Winner team ID required")
    private Long winnerTeamId;
}