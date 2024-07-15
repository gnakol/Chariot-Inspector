package fr_scapartois_auto.chariot_inspector.issue.controller;

import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import fr_scapartois_auto.chariot_inspector.issue.dtos.IssueDTO;
import fr_scapartois_auto.chariot_inspector.issue.services.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("issue")
public class IssueController {

    private final IssueService issueService;

    @GetMapping("all-issue")
    public ResponseEntity<Page<IssueDTO>> allIssue(Pageable pageable)
    {
        return ResponseEntity.ok(this.issueService.all(pageable));
    }

    @PostMapping("add-new-issue")
    public ResponseEntity<IssueDTO> addNewIssue(@Validated @RequestBody IssueDTO issueDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.issueService.add(issueDTO));
    }

    @PutMapping("update-issue/{idIssue}")
    public ResponseEntity<IssueDTO> updateIssue(@Validated @PathVariable Long idIssue, @RequestBody IssueDTO issue)
    {
        return ResponseEntity.status(202).body(this.issueService.update(idIssue, issue));
    }

    @DeleteMapping("remove-issue/{idIssue}")
    public ResponseEntity<String> removeIssue(@Validated @PathVariable Long idIssue)
    {
        this.issueService.remove(idIssue);

        return ResponseEntity.status(202).body("Issue with id : " +idIssue+ " was successfully remove");
    }

    @DeleteMapping("remove-issue-by-range-id/{startId}/{endId}")
    public ResponseEntity<String> removeIssueByIdRange(@Validated @PathVariable Long startId, @PathVariable Long endId)
    {
        this.issueService.removeIssueByIdRange(startId, endId);

        return ResponseEntity.status(202).body("Remove by range id issue was successfully");
    }

    @DeleteMapping("remove-issue-by-choose-id")
    public ResponseEntity<String> removeIssueByChooseId(@Validated @RequestBody List<Long> listIdsIssue)
    {
        this.issueService.removeIssueByChooseId(listIdsIssue);

        return ResponseEntity.status(202).body("Remove Issue by choose id was successfully");
    }

    @GetMapping("get-issue-by-id/{idIssue}")
    public ResponseEntity<IssueDTO> getByIdIssue(@Validated @PathVariable Long idIssue)
    {
        return this.issueService.getById(idIssue)
                .map(issue -> {
                    log.info("issue with id : " +idIssue+ " was found");
                    return new ResponseEntity<>(issue, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("issue with id : " +idIssue+ " was not found");
                    throw  new RuntimeException("sorry this id : "+idIssue+" was not found");
                });
    }

    @GetMapping("all-issue-by-account/{idAccount}")
    public ResponseEntity<Page<IssueDTO>> allIssueByAccount(@Validated @PathVariable Long idAccount, Pageable pageable)
    {
        return ResponseEntity.ok(this.issueService.allIssueByAccount(idAccount, pageable));
    }

    @GetMapping("get-id-issue-by-work-session-id/{workSessionId}")
    public ResponseEntity<Long> getIdIssueByWorkSessionId(@PathVariable String workSessionId) {

        Long idIssue =  this.issueService.getIdIssueByWorkSessionId(workSessionId);


        return ResponseEntity.ok(idIssue);
    }

    @GetMapping("all-issue-with-description")
    public ResponseEntity<Page<IssueDTO>> getIssuesWithDescription(Pageable pageable) {
        return ResponseEntity.ok(issueService.allIssuesWithDescription(pageable));
    }

/*    @GetMapping("all-issue-by-team-and-date")
    public ResponseEntity<Page<IssueDTO>> allIssueByTeamAndDateWork(@Validated @RequestParam String team, @RequestParam LocalDateTime shiftStart, @RequestParam LocalDateTime shiftEnd, Pageable pageable)
    {
        return ResponseEntity.ok(this.issueService.allUnresolvedIssuesByTeamAndShift(team, shiftStart, shiftEnd, pageable));
    }*/
}
