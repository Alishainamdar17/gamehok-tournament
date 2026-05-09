package com.gamehok.tournament.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class TeamRequest {
    @NotBlank
    private String name;

    @NotEmpty
    private List<Long> memberIds;
}

