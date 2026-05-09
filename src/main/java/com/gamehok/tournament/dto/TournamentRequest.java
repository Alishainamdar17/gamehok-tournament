package com.gamehok.tournament.dto;

import com.gamehok.tournament.enums.TournamentType;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class TournamentRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Type is required")
    private TournamentType type;

    @Min(value = 2, message = "Min 2 teams required")
    @Max(value = 64, message = "Max 64 teams allowed")
    private int maxTeams;
}

