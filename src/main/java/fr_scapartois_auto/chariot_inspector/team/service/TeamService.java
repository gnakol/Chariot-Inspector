package fr_scapartois_auto.chariot_inspector.team.service;

import fr_scapartois_auto.chariot_inspector.team.bean.Team;
import fr_scapartois_auto.chariot_inspector.team.dto.TeamDTO;
import fr_scapartois_auto.chariot_inspector.team.mapper.TeamMapper;
import fr_scapartois_auto.chariot_inspector.team.mapper.TeamMapperImpl;
import fr_scapartois_auto.chariot_inspector.team.repository.TeamRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService implements Webservices<TeamDTO> {

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper = new TeamMapperImpl();


    @Override
    public Page<TeamDTO> all(Pageable pageable) {
        return this.teamRepository.findAll(pageable)
                .map(this.teamMapper::fromTeam);
    }

    @Override
    public TeamDTO add(TeamDTO e) {
        return this.teamMapper.fromTeam(this.teamRepository.save(this.teamMapper.fromTeamDTO(e)));
    }

    @Override
    public TeamDTO update(Long id, TeamDTO e) {
        return this.teamMapper.fromTeam(this.teamRepository.findById(id)
                .map(team -> {
                    if (e.getName() != null)
                        team.setName(e.getName());

                    return this.teamRepository.save(team);
                })
                .orElseThrow(() -> new RuntimeException("Team with id :" +id+ " was not found")));
    }

    @Override
    public void remove(Long id) {

        Optional<Team> team = this.teamRepository.findById(id);

        if (team.isEmpty())
            throw new RuntimeException("Team id was not found");

        this.teamRepository.delete(team.get());

    }

    @Override
    public Optional<TeamDTO> getById(Long id) {
        return this.teamRepository.findById(id)
                .map(this.teamMapper::fromTeam);
    }
}
