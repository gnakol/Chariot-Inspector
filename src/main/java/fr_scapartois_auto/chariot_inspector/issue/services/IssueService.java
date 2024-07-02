package fr_scapartois_auto.chariot_inspector.issue.services;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import fr_scapartois_auto.chariot_inspector.issue.dtos.IssueDTO;
import fr_scapartois_auto.chariot_inspector.issue.mappers.IssueMapper;
import fr_scapartois_auto.chariot_inspector.issue.mappers.IssueMapperImpl;
import fr_scapartois_auto.chariot_inspector.issue.repositories.IssueRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueService implements Webservices<IssueDTO> {

    private final IssueRepository issueRepository;

    private final IssueMapper issueMapper = new IssueMapperImpl();

    private final AccountRepository accountRepository;


    @Override
    public Page<IssueDTO> all(Pageable pageable) {
        return this.issueRepository.findAll(pageable)
                .map(this.issueMapper::fromIssue);
    }

    @Override
    public IssueDTO add(IssueDTO e) {

        Issue issue = this.issueMapper.fromIssueDTO(e);

        Optional<Account> account = this.accountRepository.findById(e.getAccountId());

        if (account.isPresent())
        {
            issue.setAccount(account.get());
            Issue savedIssue = this.issueRepository.save(issue);

            return this.issueMapper.fromIssue(savedIssue);
        }
        else
            throw new RuntimeException("Account was not found");
    }

    @Override
    public IssueDTO update(Long id, IssueDTO e) {
        return this.issueMapper.fromIssue(this.issueRepository.findById(id)
                .map(issue -> {
                    if (e.getDescription() != null)
                        issue.setDescription(e.getDescription());
                    if (e.getCreatedAt() != null)
                        issue.setCreatedAt(e.getCreatedAt());
                    if (e.getAccountId() != null)
                    {
                        Optional<Account> account = this.accountRepository.findById(e.getAccountId());

                        issue.setAccount(account.get());
                    }

                    return this.issueRepository.save(issue);
                })
                .orElseThrow(() -> new RuntimeException("Account with id : " +id+ " was not found")));
    }

    @Override
    public void remove(Long id) {

        Optional<Issue> issue = this.issueRepository.findById(id);

        if (issue.isEmpty())
            throw  new RuntimeException("Issue with id :"+id+ " was not found");

        this.issueRepository.delete(issue.get());

    }

    @Override
    public Optional<IssueDTO> getById(Long id) {
        return this.issueRepository.findById(id)
                .map(this.issueMapper::fromIssue);
    }

    public Page<IssueDTO> allIssueByAccount(Long idAccount, Pageable pageable)
    {
        Optional<Account> account = this.accountRepository.findById(idAccount);

        if (account.isEmpty())
            throw new RuntimeException("Account with id : " +idAccount+ " was not found");

        List<Issue> issues = this.issueRepository.findByAccount(account.get());
        List<IssueDTO> issueDTOS = issues.stream().map(this.issueMapper::fromIssue).collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), issueDTOS.size());
        int end = Math.min((start + pageable.getPageSize()), issueDTOS.size());

        return new PageImpl<>(issueDTOS.subList(start, end), pageable, issueDTOS.size());


    }
}