package com.gamehok.tournament.service;


// ─────────────────────────────────────────
// MatchService.java
// ─────────────────────────────────────────


import com.gamehok.tournament.dto.MatchResultRequest;
import com.gamehok.tournament.enums.MatchStatus;
import com.gamehok.tournament.enums.TournamentStatus;
import com.gamehok.tournament.exception.ResourceNotFoundException;
import com.gamehok.tournament.repository.ParticipantRepository;
import com.gamehok.tournament.repository.TeamRepository;
import com.gamehok.tournament.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import com.gamehok.tournament.model.Match;
import com.gamehok.tournament.model.Team;
import com.gamehok.tournament.model.Tournament;
import org.springframework.stereotype.Service;
import com.gamehok.tournament.repository.MatchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final ParticipantRepository participantRepository;

    public Match getMatch(Long matchId) {
        return matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + matchId));
    }

    public List<Match> getMatchesByTournament(Long tournamentId) {
        return matchRepository.findByTournamentId(tournamentId);
    }

    public Match submitResult(Long matchId, MatchResultRequest req) {
        Match match = getMatch(matchId);

        if (match.getStatus() == MatchStatus.COMPLETED)
            throw new IllegalStateException("Match already completed");

        Team winner = teamRepository.findById(req.getWinnerTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        if (!winner.equals(match.getTeam1()) && !winner.equals(match.getTeam2()))
            throw new IllegalArgumentException("Winner must be one of the match teams");

        // Mark loser eliminated
        Team loser = winner.equals(match.getTeam1()) ? match.getTeam2() : match.getTeam1();
        eliminateTeam(match.getTournament().getId(), loser.getId());

        match.setWinner(winner);
        match.setStatus(MatchStatus.COMPLETED);
        matchRepository.save(match);

        // Check if round complete → generate next round
        advanceRoundIfNeeded(match.getTournament());

        return match;
    }

    private void eliminateTeam(Long tournamentId, Long teamId) {
        participantRepository.findByTournamentId(tournamentId).stream()
                .filter(p -> p.getTeam().getId().equals(teamId))
                .findFirst()
                .ifPresent(p -> {
                    p.setEliminated(true);
                    participantRepository.save(p);
                });
    }

    private void advanceRoundIfNeeded(Tournament tournament) {
        List<Match> pending = matchRepository
                .findByTournamentIdAndStatus(tournament.getId(), MatchStatus.PENDING);

        if (!pending.isEmpty()) return; // round not done

        // Get winners of completed round
        List<Match> allMatches = matchRepository.findByTournamentId(tournament.getId());
        int lastRound = allMatches.stream()
                .mapToInt(Match::getRoundNumber).max().orElse(1);

        List<Match> lastRoundMatches = matchRepository
                .findByTournamentIdAndRoundNumber(tournament.getId(), lastRound);

        List<Team> winners = lastRoundMatches.stream()
                .map(Match::getWinner).filter(w -> w != null).toList();

        if (winners.size() == 1) {
            // Tournament over!
            tournament.setStatus(TournamentStatus.COMPLETED);
            tournamentRepository.save(tournament);
            return;
        }

        // Generate next round
        for (int i = 0; i < winners.size() / 2; i++) {
            Match m = Match.builder()
                    .tournament(tournament)
                    .team1(winners.get(2 * i))
                    .team2(winners.get(2 * i + 1))
                    .roundNumber(lastRound + 1)
                    .bracketPosition(i + 1)
                    .status(MatchStatus.PENDING)
                    .build();
            matchRepository.save(m);
        }
    }
}
