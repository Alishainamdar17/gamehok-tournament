package com.gamehok.tournament.repository;

import com.gamehok.tournament.enums.TournamentStatus;
import com.gamehok.tournament.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findByStatus(TournamentStatus status);
}