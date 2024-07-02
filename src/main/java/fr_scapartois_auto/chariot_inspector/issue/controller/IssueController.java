package fr_scapartois_auto.chariot_inspector.issue.controller;

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
                    throw  new RuntimeException("sorry this id was not found");
                });
    }

    @GetMapping("all-issue-by-account/{idAccount}")
    public ResponseEntity<Page<IssueDTO>> allIssueByAccount(@Validated @PathVariable Long idAccount, Pageable pageable)
    {
        return ResponseEntity.ok(this.issueService.allIssueByAccount(idAccount, pageable));
    }
}
