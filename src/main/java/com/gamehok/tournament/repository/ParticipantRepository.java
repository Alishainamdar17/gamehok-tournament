package com.gamehok.tournament.repository;


import com.gamehok.tournament.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByTournamentId(Long tournamentId);
    List<Participant> findByTournamentIdAndEliminatedFalse(Long tournamentId);
    boolean existsByTournamentIdAndTeamId(Long tournamentId, Long teamId);
}