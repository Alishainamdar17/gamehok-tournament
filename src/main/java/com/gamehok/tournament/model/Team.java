package com.gamehok.tournament.model;

// ─────────────────────────────────────────
// Team.java
// ─────────────────────────────────────────


import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity @Table(name = "teams")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "team_size", nullable = false)
    private int teamSize; // 1, 2, 3, 4, 5

    @ManyToMany
    @JoinTable(name = "team_members",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members;
}
