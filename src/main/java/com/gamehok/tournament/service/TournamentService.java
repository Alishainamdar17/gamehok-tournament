package com.gamehok.tournament.service;


import com.gamehok.tournament.dto.TournamentRequest;
import com.gamehok.tournament.enums.TournamentStatus;
import com.gamehok.tournament.exception.ResourceNotFoundException;
import com.gamehok.tournament.model.Match;
import com.gamehok.tournament.model.Participant;
import com.gamehok.tournament.model.Team;
import com.gamehok.tournament.model.Tournament;
import com.gamehok.tournament.repository.ParticipantRepository;
import com.gamehok.tournament.repository.TeamRepository;
import com.gamehok.tournament.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final ParticipantRepository participantRepository;
    private final BracketService bracketService;

    public Tournament createTournament(TournamentRequest req) {
        Tournament t = Tournament.builder()
                .name(req.getName())
                .type(req.getType())
                .maxTeams(req.getMaxTeams())
                .build();
        return tournamentRepository.save(t);
    }

    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found: " + id));
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Participant registerTeam(Long tournamentId, Long teamId) {
        Tournament tournament = getTournament(tournamentId);

        if (tournament.getStatus() != TournamentStatus.CREATED)
            throw new IllegalStateException("Tournament already started or completed");

        if (participantRepository.existsByTournamentIdAndTeamId(tournamentId, teamId))
            throw new IllegalStateException("Team already registered");

        long currentCount = participantRepository.findByTournamentId(tournamentId).size();
        if (currentCount >= tournament.getMaxTeams())
            throw new IllegalStateException("Tournament is full");

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + teamId));

        Participant p = Participant.builder()
                .tournament(tournament)
                .team(team)
                .eliminated(false)
                .build();
        return participantRepository.save(p);
    }

    public List<Match> startTournament(Long tournamentId) {
        Tournament tournament = getTournament(tournamentId);

        if (tournament.getStatus() != TournamentStatus.CREATED)
            throw new IllegalStateException("Tournament already started");

        List<Participant> participants = participantRepository.findByTournamentId(tournamentId);
        if (participants.size() < 2)
            throw new IllegalStateException("Need at least 2 teams to start");

        tournament.setStatus(TournamentStatus.ONGOING);
        tournamentRepository.save(tournament);

        return bracketService.generateBracket(tournament, participants);
    }

    public List<Match> getBracket(Long tournamentId) {
        getTournament(tournamentId); // validate exists
        return bracketService.getBracket(tournamentId);
    }
}