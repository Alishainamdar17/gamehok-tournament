package com.gamehok.tournament.repository;

import com.gamehok.tournament.enums.MatchStatus;
import com.gamehok.tournament.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByTournamentId(Long tournamentId);
    List<Match> findByTournamentIdAndRoundNumber(Long tournamentId, int round);
    List<Match> findByTournamentIdAndStatus(Long tournamentId, MatchStatus status);
}
