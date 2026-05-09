package com.gamehok.tournament.model;

import com.gamehok.tournament.enums.MatchStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team1_id")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id")
    private Team team2;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Team winner;

    @Column(name = "round_number")
    private int roundNumber;

    @Column(name = "bracket_position")
    private int bracketPosition;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @PrePersist
    public void prePersist() {
        status = MatchStatus.PENDING;
    }
}