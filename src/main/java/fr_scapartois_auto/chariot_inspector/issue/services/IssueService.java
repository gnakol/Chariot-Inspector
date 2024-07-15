package fr_scapartois_auto.chariot_inspector.issue.services;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapperImpl;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import fr_scapartois_auto.chariot_inspector.issue.dtos.IssueDTO;
import fr_scapartois_auto.chariot_inspector.issue.mappers.IssueMapper;
import fr_scapartois_auto.chariot_inspector.issue.mappers.IssueMapperImpl;
import fr_scapartois_auto.chariot_inspector.issue.repositories.IssueRepository;
import fr_scapartois_auto.chariot_inspector.session.service.WorkSessionService;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    private final AccountMapper accountMapper = new AccountMapperImpl();

    private final WorkSessionService workSessionService;


    @Override
    public Page<IssueDTO> all(Pageable pageable) {
        return this.issueRepository.findAll(pageable)
                .map(this.issueMapper::fromIssue);
    }

    @Override
    public IssueDTO add(IssueDTO e) {

        if (e.getDescription() == null)
            e.setDescription("RAS");

        Issue issue = this.issueMapper.fromIssueDTO(e);


        Optional<Account> account = this.accountRepository.findById(e.getAccountId());

        if (account.isPresent())
        {
            String workSessionId = this.workSessionService.getActiveWorkSession(account.get().getIdAccount())
                            .orElseThrow(() -> new RuntimeException("No active work session found"))
                                    .getWorkSessionId();

            issue.setAccount(account.get());
            issue.setWorkSessionId(workSessionId);
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

    @Transactional
    public void removeIssueByIdRange(Long startId, Long endId)
    {
        this.issueRepository.deleteByIdRange(startId, endId);
    }

    @Transactional
    public void removeIssueByChooseId(List<Long> listIdIssue)
    {
        this.issueRepository.deleteByIds(listIdIssue);
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

    public List<IssueDTO> allIssueByWorkSessionId(String workSessionId)
    {
        return this.issueRepository.findByWorkSessionId(workSessionId)
                .stream()
                .map(this.issueMapper::fromIssue)
                .collect(Collectors.toList());
    }

    public List<String> getWorkSessionIdsByAccountId(Long idAccount)
    {
        return this.issueRepository.findDistinctWorkSessionIdsByAccountId(idAccount);
    }

    public Long getIdIssueByWorkSessionId(String workSessionId) {
        // Récupérer les issues associées au workSessionId
        List<Issue> issues = this.issueRepository.findByWorkSessionId(workSessionId);

        // Si aucune issue n'est trouvée, lancer une exception
        if (issues.isEmpty()) {
            throw new RuntimeException("No issues found for workSessionId: " + workSessionId);
        }

        // Retourner l'ID de la première issue trouvée
        return issues.get(0).getIdIssue();
    }


    @Transactional
    public Page<IssueDTO> allIssuesWithDescription(Pageable pageable) {
        return issueRepository.findIssuesWithDescription(pageable)
                .map(issueMapper::fromIssue);
    }

/*    public Page<IssueDTO> allUnresolvedIssuesByTeamAndShift(String team, LocalDateTime shiftStart, LocalDateTime shiftEnd, Pageable pageable)
    {
        List<Issue> issues = this.issueRepository.findUnresolvedIssuesByTeamAndShift(team, shiftStart, shiftEnd);

        List<IssueDTO> issueDTOS = issues.stream().map(this.issueMapper::fromIssue).collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), issueDTOS.size());
        int end = Math.min((start + pageable.getPageSize()), issueDTOS.size());

        return new  PageImpl<>(issueDTOS.subList(start, end), pageable, issueDTOS.size());
    }*/

}
