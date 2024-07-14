package fr_scapartois_auto.chariot_inspector.team.controller;

import fr_scapartois_auto.chariot_inspector.team.dto.TeamDTO;
import fr_scapartois_auto.chariot_inspector.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("team")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("all-team")
    public ResponseEntity<Page<TeamDTO>> allTeam(Pageable pageable)
    {
        return ResponseEntity.ok(this.teamService.all(pageable));
    }

    @PostMapping("add-new-team")
    public ResponseEntity<TeamDTO> addNewTeam(@Validated @RequestBody TeamDTO teamDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.teamService.add(teamDTO));
    }

    @PutMapping("update-team/{idTeam}")
    public ResponseEntity<TeamDTO> updateTeam(@Validated @PathVariable Long idTeam, @RequestBody TeamDTO teamDTO)
    {
        return ResponseEntity.status(202).body(this.teamService.update(idTeam, teamDTO));
    }

    @DeleteMapping("remove-team/{idTeam}")
    public ResponseEntity<String> removeTeam(@Validated @PathVariable Long idTeam)
    {
        this.teamService.remove(idTeam);

        return ResponseEntity.status(202).body("Team was successfully remove");
    }

    @GetMapping("get-team-by-id/{idTeam}")
    public ResponseEntity<TeamDTO> getTeamById(@Validated @PathVariable Long idTeam)
    {
        return this.teamService.getById(idTeam)
                .map(teamDTO -> {
                    log.info("team with id : "+idTeam+ " was found");
                    return new ResponseEntity<>(teamDTO, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("Team with id :" +idTeam+ " was not found");
                    throw  new RuntimeException("Sorry this team was not found");
                });
    }
}
