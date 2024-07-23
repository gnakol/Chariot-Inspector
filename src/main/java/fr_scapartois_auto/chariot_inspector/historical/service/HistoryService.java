package fr_scapartois_auto.chariot_inspector.historical.service;

import fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.dto.TaurusDTO;
import fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.service.TaurusService;
import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.account.services.AccountService;
import fr_scapartois_auto.chariot_inspector.account_team.bean.AccountTeam;
import fr_scapartois_auto.chariot_inspector.account_team.dto.AccountTeamDTO;
import fr_scapartois_auto.chariot_inspector.account_team.mapper.AccountTeamMapper;
import fr_scapartois_auto.chariot_inspector.account_team.mapper.AccountTeamMapperImpl;
import fr_scapartois_auto.chariot_inspector.account_team.service.AccountTeamService;
import fr_scapartois_auto.chariot_inspector.battery_usage.dto.BatteryUsageDTO;
import fr_scapartois_auto.chariot_inspector.battery_usage.service.BatteryUsageService;
import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.cart.services.CartService;
import fr_scapartois_auto.chariot_inspector.historical.bean.HistoryEntryDTO;
import fr_scapartois_auto.chariot_inspector.issue.dtos.IssueDTO;
import fr_scapartois_auto.chariot_inspector.issue.services.IssueService;
import fr_scapartois_auto.chariot_inspector.pickup.dto.PickupDTO;
import fr_scapartois_auto.chariot_inspector.pickup.service.PickupService;
import fr_scapartois_auto.chariot_inspector.shitf.bean.Shift;
import fr_scapartois_auto.chariot_inspector.shitf.repository.ShiftRepository;
import fr_scapartois_auto.chariot_inspector.taurus_usage.dto.TaurusUsageDTO;
import fr_scapartois_auto.chariot_inspector.taurus_usage.service.TaurusUsageService;
import fr_scapartois_auto.chariot_inspector.team.bean.Team;
import fr_scapartois_auto.chariot_inspector.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final AccountService accountService;

    private final TaurusUsageService taurusUsageService;

    private final IssueService issueService;

    private final PickupService pickupService;

    private final BatteryUsageService batteryUsageService;

    private final TaurusService taurusService;

    private final CartService cartService;

    private final AccountTeamService accountTeamService;

    private final AccountTeamMapper accountTeamMapper = new AccountTeamMapperImpl();

    private final ShiftRepository shiftRepository;

    private final TeamRepository teamRepository;

    public Page<HistoryEntryDTO> getHistory(Long idAccount, Pageable pageable) {
        Optional<AccountDTO> accountDTOOptional = this.accountService.getById(idAccount);

        if (!accountDTOOptional.isPresent()) {
            throw new RuntimeException("Account not found");
        }

        AccountDTO accountDTO = accountDTOOptional.get();
        List<HistoryEntryDTO> historyEntryDTOS = new ArrayList<>();

        // Collect distinct work session IDs from all related services
        Set<String> workSessionIds = new HashSet<>();
        workSessionIds.addAll(taurusUsageService.getWorkSessionIdsByAccountId(idAccount));
        workSessionIds.addAll(pickupService.getWorkSessionIdsByAccountId(idAccount));
        workSessionIds.addAll(issueService.getWorkSessionIdsByAccountId(idAccount));

        // Remove null work session IDs
        workSessionIds.removeIf(Objects::isNull);

        System.out.println("********************************************************");
        System.out.println("Contenus workSessionIds des trois block concerner");
        System.out.println(workSessionIds);
        System.out.println("********************************************************");

        for (String workSessionId : workSessionIds) {
            HistoryEntryDTO entryDTO = new HistoryEntryDTO();
            entryDTO.setName(accountDTO.getName());
            entryDTO.setFirstName(accountDTO.getFirstName());

            boolean hasRelevantData = false;

            // Retrieve and set taurus usage
            List<TaurusUsageDTO> taurusUsages = this.taurusUsageService.allTaurusUsageByWorkSessionId(workSessionId);
            if (!taurusUsages.isEmpty()) {
                Optional<TaurusDTO> taurusDTOOptional = this.taurusService.getById(taurusUsages.get(0).getTaurusId());
                if (taurusDTOOptional.isPresent()) {
                    entryDTO.setTaurusNumber(taurusDTOOptional.get().getTaurusNumber());
                    entryDTO.setUsageDate(taurusUsages.get(0).getUsageDate());
                    hasRelevantData = true;
                }
            }

            // Retrieve and set pickup data
            List<PickupDTO> pickupDTOS = this.pickupService.allPickupByWorkSessionId(workSessionId);
            if (!pickupDTOS.isEmpty()) {
                PickupDTO pickupDTO = pickupDTOS.get(0);
                Optional<CartDTO> cartDTOOptional = this.cartService.getById(pickupDTO.getCartId());
                if (cartDTOOptional.isPresent()) {
                    entryDTO.setCartNumber(cartDTOOptional.get().getCartNumber());
                    hasRelevantData = true;
                }

                List<BatteryUsageDTO> batteryUsageDTOS = this.batteryUsageService.allBatteryUsageByWorkSessionId(workSessionId);
                entryDTO.setBatteryDTOS(batteryUsageDTOS.stream()
                        .map(batteryUsage -> this.batteryUsageService.getBatteryById(batteryUsage.getBatteryId()))
                        .collect(Collectors.toList()));
            }

            // Retrieve and set issues
            List<IssueDTO> issueDTOS = this.issueService.allIssueByWorkSessionId(workSessionId);
            if (!issueDTOS.isEmpty()) {
                entryDTO.setIssueDescription(issueDTOS.stream().map(IssueDTO::getDescription).collect(Collectors.joining(", ")));
                hasRelevantData = true;
            }

            // Retrieve and set team and shift data
            Optional<AccountTeamDTO> accountTeamDTOOptional = this.accountTeamService.findByWorkSessionId(workSessionId);

            if (accountTeamDTOOptional.isPresent()) {

                AccountTeamDTO accountTeamDTO = accountTeamDTOOptional.get();
                AccountTeam accountTeam = this.accountTeamMapper.fromAccountTeamDTO(accountTeamDTO);

                Optional<Shift> shift = this.shiftRepository.findById(accountTeam.getShift().getIdShift());
                Optional<Team> team = this.teamRepository.findById(accountTeam.getTeam().getIdTeam());

                if (shift.isPresent() && team.isPresent()) {
                    entryDTO.setTeamName(team.get().getName());
                    entryDTO.setShiftName(shift.get().getName());
                }
                hasRelevantData = true;
            }

            // Add entry only if it contains relevant data
            if (hasRelevantData) {
                historyEntryDTOS.add(entryDTO);
            }
        }

        int start = Math.min((int) pageable.getOffset(), historyEntryDTOS.size());
        int end = Math.min((start + pageable.getPageSize()), historyEntryDTOS.size());

        List<HistoryEntryDTO> historyEntryDTOList = historyEntryDTOS.subList(start, end);

        return new PageImpl<>(historyEntryDTOList, pageable, historyEntryDTOS.size());
    }
}
