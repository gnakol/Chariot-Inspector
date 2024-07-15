package fr_scapartois_auto.chariot_inspector.account_team.service;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.account_team.bean.AccountTeam;
import fr_scapartois_auto.chariot_inspector.account_team.dto.AccountTeamDTO;
import fr_scapartois_auto.chariot_inspector.account_team.mapper.AccountTeamMapper;
import fr_scapartois_auto.chariot_inspector.account_team.mapper.AccountTeamMapperImpl;
import fr_scapartois_auto.chariot_inspector.account_team.repository.AccountTeamRepository;
import fr_scapartois_auto.chariot_inspector.team.bean.Team;
import fr_scapartois_auto.chariot_inspector.team.repository.TeamRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountTeamService implements Webservices<AccountTeamDTO> {

    private final AccountTeamRepository accountTeamRepository;

    private final AccountTeamMapper accountTeamMapper = new AccountTeamMapperImpl();

    private final AccountRepository accountRepository;

    private final TeamRepository teamRepository;

    @Override
    public Page<AccountTeamDTO> all(Pageable pageable) {
        return this.accountTeamRepository.findAll(pageable)
                .map(this.accountTeamMapper::fromAccountTeam);
    }

    @Override
    public AccountTeamDTO add(AccountTeamDTO e) {

        AccountTeam accountTeam = this.accountTeamMapper.fromAccountTeamDTO(e);

        Optional<Account> account = this.accountRepository.findById(accountTeam.getAccount().getIdAccount());
        Optional<Team> team = this.teamRepository.findById(accountTeam.getTeam().getIdTeam());

        if (account.isPresent() && team.isPresent())
        {
            accountTeam.setAccount(account.get());
            accountTeam.setTeam(team.get());

            AccountTeam accountTeamSaved = this.accountTeamRepository.save(accountTeam);

            return this.accountTeamMapper.fromAccountTeam(accountTeamSaved);
        }
        else
            throw new RuntimeException("Account and Team was not found");

    }

    @Override
    public AccountTeamDTO update(Long id, AccountTeamDTO e) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<AccountTeamDTO> getById(Long id) {
        return this.accountTeamRepository.findById(id)
                .map(this.accountTeamMapper::fromAccountTeam);
    }
}
