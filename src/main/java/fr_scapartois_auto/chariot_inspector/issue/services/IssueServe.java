package fr_scapartois_auto.chariot_inspector.issue.services;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import fr_scapartois_auto.chariot_inspector.issue.repositories.IssueRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueServe implements Webservices<Issue> {

    private final IssueRepository issueRepository;

    private final AccountRepository accountRepository;


    @Override
    public Page<Issue> all(Pageable pageable) {
        return this.issueRepository.findAll(pageable);
    }

    @Override
    public Issue add(Issue e) {

        return this.issueRepository.save(e);
    }

    @Override
    public Issue update(Long id, Issue e) {
        return this.issueRepository.findById(id)
                .map(issue -> {
                    if (e.getDescription() != null)
                        issue.setDescription(e.getDescription());
                    if (e.getAccount() != null)
                        issue.setAccount(e.getAccount());

                    return this.issueRepository.save(issue);
                })
                .orElseThrow(() -> new RuntimeException("issue id not found"));
    }

    @Override
    public void remove(Long id) {

        Optional<Issue> issue = this.issueRepository.findById(id);

        if (issue.isEmpty())
        {
            throw new RuntimeException("not found");
        }

        this.issueRepository.delete(issue.get());

    }

    @Override
    public Optional<Issue> getById(Long id) {
        return this.issueRepository.findById(id)
                .map(Optional::of)
                .orElseThrow(() -> new RuntimeException("not found"));
    }
}
