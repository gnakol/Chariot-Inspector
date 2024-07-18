package fr_scapartois_auto.chariot_inspector.account_team.service;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.account_team.bean.AccountTeam;
import fr_scapartois_auto.chariot_inspector.account_team.dto.AccountTeamDTO;
import fr_scapartois_auto.chariot_inspector.account_team.mapper.AccountTeamMapper;
import fr_scapartois_auto.chariot_inspector.account_team.mapper.AccountTeamMapperImpl;
import fr_scapartois_auto.chariot_inspector.account_team.repository.AccountTeamRepository;
import fr_scapartois_auto.chariot_inspector.session.service.WorkSessionService;
import fr_scapartois_auto.chariot_inspector.shitf.bean.Shift;
import fr_scapartois_auto.chariot_inspector.shitf.dto.ShiftDTO;
import fr_scapartois_auto.chariot_inspector.shitf.mapper.ShiftMapper;
import fr_scapartois_auto.chariot_inspector.shitf.mapper.ShiftMapperImpl;
import fr_scapartois_auto.chariot_inspector.shitf.service.ShiftService;
import fr_scapartois_auto.chariot_inspector.team.bean.Team;
import fr_scapartois_auto.chariot_inspector.team.repository.TeamRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountTeamService implements Webservices<AccountTeamDTO> {

    private final AccountTeamRepository accountTeamRepository;

    private final AccountTeamMapper accountTeamMapper = new AccountTeamMapperImpl();

    private final AccountRepository accountRepository;

    private final TeamRepository teamRepository;

    private final WorkSessionService workSessionService;

    private final ShiftService shiftService;

    private final ShiftMapper shiftMapper = new ShiftMapperImpl();

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

        Optional<AccountTeam> accountTeam = this.accountTeamRepository.findById(id);

        if (accountTeam.isEmpty())
            throw new RuntimeException("this id was not found");

        this.accountTeamRepository.delete(accountTeam.get());

    }

    @Override
    public Optional<AccountTeamDTO> getById(Long id) {
        return this.accountTeamRepository.findById(id)
                .map(this.accountTeamMapper::fromAccountTeam);
    }

    public AccountTeamDTO addOrUpdateAccountTeam(AccountTeamDTO e) {

        AccountTeam accountTeam = this.accountTeamMapper.fromAccountTeamDTO(e);

        Optional<Account> account = this.accountRepository.findById(accountTeam.getAccount().getIdAccount());
        Optional<Team> team = this.teamRepository.findById(accountTeam.getTeam().getIdTeam());

        if (account.isPresent() && team.isPresent()) {
            String workSessionId = this.workSessionService.getActiveWorkSession(account.get().getIdAccount())
                    .orElseThrow(() -> new RuntimeException("No active work session found"))
                    .getWorkSessionId();

            // Déterminer le shift basé sur l'heure actuelle
            LocalTime currentTime = LocalTime.now();
            ShiftDTO currentShiftDTO = shiftService.determineShift(currentTime);
            Shift currentShift = this.shiftMapper.fromShiftDTO(currentShiftDTO);

            LocalDate usageDate = e.getStartDate();

            Optional<AccountTeam> accountTeamOpt = accountTeamRepository.findByAccountIdAndCurrentDate(account.get().getIdAccount(), usageDate);
            if (accountTeamOpt.isPresent()) {
                accountTeam = accountTeamOpt.get();
                accountTeam.setShift(currentShift);
                accountTeam.setWorkSessionId(workSessionId);
            } else {
                accountTeam.setStartDate(usageDate);
                accountTeam.setEndDate(currentShift.getEndTime().isBefore(currentShift.getStartTime()) ? usageDate.plusDays(1) : usageDate);
                accountTeam.setShift(currentShift);
                accountTeam.setWorkSessionId(workSessionId);
                accountTeam.setAccount(account.get());
                accountTeam.setTeam(team.get());
            }

            AccountTeam accountTeamSaved = this.accountTeamRepository.save(accountTeam);
            AccountTeamDTO savedDTO = this.accountTeamMapper.fromAccountTeam(accountTeamSaved);
            savedDTO.setShiftId(currentShift.getIdShift()); // Mise à jour de shiftId
            return savedDTO;
        } else {
            throw new RuntimeException("Account or Team not found");
        }
    }

    @Transactional
    public Optional<AccountTeamDTO> findByWorkSessionId(String workSessionId) {
        Optional<AccountTeam> accountTeamOpt = this.accountTeamRepository.findByWorkSessionId(workSessionId);
        return accountTeamOpt.map(accountTeamMapper::fromAccountTeam);
    }

    public Optional<AccountTeamDTO> getAccountTeamByWorkSessionId(String workSessionId)
    {
        Optional<AccountTeam> accountTeam = this.accountTeamRepository.findByWorkSessionId(workSessionId);

        if (accountTeam.isEmpty())
            throw new RuntimeException("this work session id was not found");

        return accountTeam.map(this.accountTeamMapper::fromAccountTeam);
    }
}
