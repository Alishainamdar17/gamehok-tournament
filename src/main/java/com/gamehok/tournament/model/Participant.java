package com.gamehok.tournament.model;
//Participant.java
// ─────────────────────────────────────────


import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "participants")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Participant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private boolean eliminated = false;
}
