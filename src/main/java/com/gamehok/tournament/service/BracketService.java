package com.gamehok.tournament.service;


import com.gamehok.tournament.enums.MatchStatus;
import com.gamehok.tournament.model.Match;
import com.gamehok.tournament.model.Participant;
import com.gamehok.tournament.model.Team;
import com.gamehok.tournament.model.Tournament;
import com.gamehok.tournament.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BracketService {
    private final MatchRepository matchRepository;

    public List<Match> generateBracket(Tournament tournament, List<Participant> participants) {
        List<Team> teams = new ArrayList<>();
        for (Participant p : participants) teams.add(p.getTeam());

        // Shuffle for random seeding
        Collections.shuffle(teams);

        // Pad to next power of 2 (bye system)
        int size = nextPowerOfTwo(teams.size());
        while (teams.size() < size) teams.add(null); // null = BYE

        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < size / 2; i++) {
            Team t1 = teams.get(2 * i);
            Team t2 = teams.get(2 * i + 1);

            Match m = Match.builder()
                    .tournament(tournament)
                    .team1(t1)
                    .team2(t2)
                    .roundNumber(1)
                    .bracketPosition(i + 1)
                    .status(t2 == null ? MatchStatus.COMPLETED : MatchStatus.PENDING)
                    .winner(t2 == null ? t1 : null) // auto-win on BYE
                    .build();
            matches.add(matchRepository.save(m));
        }
        return matches;
    }

    public List<Match> getBracket(Long tournamentId) {
        return matchRepository.findByTournamentId(tournamentId);
    }

    private int nextPowerOfTwo(int n) {
        int p = 1;
        while (p < n) p *= 2;
        return p;
    }
}

