package com.gamehok.tournament.service;


import com.gamehok.tournament.dto.TeamRequest;
import com.gamehok.tournament.exception.ResourceNotFoundException;
import com.gamehok.tournament.model.Team;
import com.gamehok.tournament.model.User;
import com.gamehok.tournament.repository.TeamRepository;
import com.gamehok.tournament.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Team createTeam(TeamRequest req) {
        List<User> members = userRepository.findAllById(req.getMemberIds());
        if (members.size() != req.getMemberIds().size())
            throw new ResourceNotFoundException("One or more users not found");

        Team team = Team.builder()
                .name(req.getName())
                .teamSize(members.size())
                .members(members)
                .build();
        return teamRepository.save(team);
    }

    public Team getTeam(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + id));
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}

