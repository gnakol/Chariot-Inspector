package fr_scapartois_auto.chariot_inspector.issue.controller;

import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import fr_scapartois_auto.chariot_inspector.issue.services.IssueServe;
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

    private final IssueServe issueServe;

    @GetMapping("all-issue")
    public ResponseEntity<Page<Issue>> allIssue(Pageable pageable)
    {
        return ResponseEntity.ok(this.issueServe.all(pageable));
    }

    @PostMapping("add-new-issue")
    public ResponseEntity<Issue> addNewIssue(@Validated @RequestBody Issue issue)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.issueServe.add(issue));
    }

    @PutMapping("update-issue/{idIssue}")
    public ResponseEntity<Issue> updateIssue(@Validated @PathVariable Long idIssue, @RequestBody Issue issue)
    {
        return ResponseEntity.status(202).body(this.issueServe.update(idIssue, issue));
    }

    @DeleteMapping("remove-issue/{idIssue}")
    public ResponseEntity<String> removeIssue(@Validated @PathVariable Long idIssue)
    {
        this.issueServe.remove(idIssue);

        return ResponseEntity.status(202).body("Issue with id : " +idIssue+ " was successfully remove");
    }

    @GetMapping("get-issue-by-id/{idIssue}")
    public ResponseEntity<Issue> getByIdIssue(@Validated @PathVariable Long idIssue)
    {
        return this.issueServe.getById(idIssue)
                .map(issue -> {
                    log.info("issue with id : " +idIssue+ " was found");
                    return new ResponseEntity<>(issue, HttpStatus.OK);
                })
                .orElseThrow(() -> {
                    log.error("issue with id : " +idIssue+ " was not found");
                    throw  new RuntimeException("sorry this id was not found");
                });
    }
}
