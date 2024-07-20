package fr_scapartois_auto.chariot_inspector.action_carried_out.services;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.action_carried_out.beans.ActionCarriedOut;
import fr_scapartois_auto.chariot_inspector.action_carried_out.dtos.ActionCarriedOutDTO;
import fr_scapartois_auto.chariot_inspector.action_carried_out.mappers.ActionCarriedOutMapper;
import fr_scapartois_auto.chariot_inspector.action_carried_out.mappers.ActionCarriedOutMapperImpl;
import fr_scapartois_auto.chariot_inspector.action_carried_out.repositories.ActionCarriedOutRepository;
import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import fr_scapartois_auto.chariot_inspector.issue.repositories.IssueRepository;
import fr_scapartois_auto.chariot_inspector.session.service.WorkSessionService;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActionCarriedOutService implements Webservices<ActionCarriedOutDTO> {

    private final ActionCarriedOutRepository actionCarriedOutRepository;

    private final AccountRepository accountRepository;

    private final IssueRepository issueRepository;

    private final ActionCarriedOutMapper actionCarriedOutMapper = new ActionCarriedOutMapperImpl();

    private final WorkSessionService workSessionService;


    @Override
    public Page<ActionCarriedOutDTO> all(Pageable pageable) {
        return this.actionCarriedOutRepository.findAll(pageable)
                .map(this.actionCarriedOutMapper::fromActionCarriedOut);
    }

    @Override
    public ActionCarriedOutDTO add(ActionCarriedOutDTO e) {

        ActionCarriedOut actionCarriedOut = this.actionCarriedOutMapper.fromActionCarriedOutDTO(e);

        Optional<Issue> issue = this.issueRepository.findById(actionCarriedOut.getIssue().getIdIssue());
        Optional<Account> account = this.accountRepository.findById(actionCarriedOut.getAccount().getIdAccount());

        if (issue.isPresent() && account.isPresent())
        {
            actionCarriedOut.setIssue(issue.get());
            actionCarriedOut.setAccount(account.get());

            return this.actionCarriedOutMapper.fromActionCarriedOut(this.actionCarriedOutRepository.save(actionCarriedOut));
        }
        else
            throw new RuntimeException("Account and Issue was not found");

    }

    @Override
    public ActionCarriedOutDTO update(Long id, ActionCarriedOutDTO e) {

        ActionCarriedOut actionCarriedOutt = this.actionCarriedOutMapper.fromActionCarriedOutDTO(e);

        return this.actionCarriedOutMapper.fromActionCarriedOut(this.actionCarriedOutRepository.findById(id)
                .map(actionCarriedOut -> {
                    if (e.getDescription() != null)
                        actionCarriedOut.setDescription(e.getDescription());
                    if (actionCarriedOutt.getIssue() != null)
                    {
                        Optional<Issue> issue = this.issueRepository.findById(actionCarriedOutt.getIssue().getIdIssue());
                        actionCarriedOut.setIssue(issue.get());
                    }

                    if (actionCarriedOutt != null)
                    {
                        Optional<Account> account = this.accountRepository.findById(actionCarriedOutt.getAccount().getIdAccount());
                        actionCarriedOut.setAccount(account.get());
                    }

                    return this.actionCarriedOutRepository.save(actionCarriedOut);
                })
                .orElseThrow(() -> new RuntimeException("Action carried out with id : " +id+ " was not found")));
    }

    @Override
    public void remove(Long id) {

        Optional<ActionCarriedOut> actionCarriedOut = this.actionCarriedOutRepository.findById(id);

        if (actionCarriedOut.isEmpty())
            throw new RuntimeException("Action carrid id was not found");

        this.actionCarriedOutRepository.delete(actionCarriedOut.get());

    }

    @Override
    public Optional<ActionCarriedOutDTO> getById(Long id) {
        return this.actionCarriedOutRepository.findById(id)
                .map(this.actionCarriedOutMapper::fromActionCarriedOut);
    }
}
