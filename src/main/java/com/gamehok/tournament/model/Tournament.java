package com.gamehok.tournament.model;

import com.gamehok.tournament.enums.TournamentStatus;
import com.gamehok.tournament.enums.TournamentType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "tournaments")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Tournament {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TournamentType type; // ONE_VS_ONE, TWO_VS_TWO, etc.

    @Enumerated(EnumType.STRING)
    private TournamentStatus status; // CREATED, ONGOING, COMPLETED

    @Column(name = "max_teams")
    private int maxTeams;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<Participant> participants;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<Match> matches;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        status = TournamentStatus.CREATED;
    }
}

