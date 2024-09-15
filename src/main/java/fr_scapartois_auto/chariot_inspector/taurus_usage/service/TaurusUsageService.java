package fr_scapartois_auto.chariot_inspector.taurus_usage.service;

import fr_scapartois_auto.chariot_inspector.taurus.bean.Taurus;
import fr_scapartois_auto.chariot_inspector.taurus.dto.TaurusDTO;
import fr_scapartois_auto.chariot_inspector.taurus.mapper.TaurusMapper;
import fr_scapartois_auto.chariot_inspector.taurus.repositorie.TaurusRepository;
import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapperImpl;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.session.service.WorkSessionService;
import fr_scapartois_auto.chariot_inspector.taurus_usage.bean.TaurusUsage;
import fr_scapartois_auto.chariot_inspector.taurus_usage.dto.TaurusUsageDTO;
import fr_scapartois_auto.chariot_inspector.taurus_usage.mapper.TaurusUsageMapper;
import fr_scapartois_auto.chariot_inspector.taurus_usage.mapper.TaurusUsageMapperImpl;
import fr_scapartois_auto.chariot_inspector.taurus_usage.repositorie.TaurusUsageRepository;
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
public class TaurusUsageService implements Webservices<TaurusUsageDTO> {

    private final AccountRepository accountRepository;

    private final TaurusRepository taurusRepository;

    private final TaurusUsageRepository taurusUsageRepository;

    private final AccountMapper accountMapper = new AccountMapperImpl();

    //private final TaurusMapper taurusMapper = new TaurusMapperImpl();

    private final TaurusUsageMapper taurusUsageMapper = new TaurusUsageMapperImpl();

    private final WorkSessionService workSessionService;


    @Override
    public Page<TaurusUsageDTO> all(Pageable pageable) {
        return this.taurusUsageRepository.findAll(pageable)
                .map(this.taurusUsageMapper::fromTaurusUsage);
    }

    @Override
    public TaurusUsageDTO add(TaurusUsageDTO e) {

        TaurusUsage taurusUsage = this.taurusUsageMapper.fromTaurusUsageDTO(e);

        Optional<Account> account = this.accountRepository.findById(taurusUsage.getAccount().getIdAccount());
        Optional<Taurus> taurus = this.taurusRepository.findById(taurusUsage.getTaurus().getIdTaurus());

        if (account.isPresent() && taurus.isPresent())
        {
            String workSessionId = this.workSessionService.getActiveWorkSession(account.get().getIdAccount())
                            .orElseThrow(() -> new RuntimeException("No active work session found"))
                                    .getWorkSessionId();

            taurusUsage.setAccount(account.get());
            taurusUsage.setTaurus(taurus.get());
            taurusUsage.setWorkSessionId(workSessionId);

            TaurusUsage savedTaurusUsage = this.taurusUsageRepository.save(taurusUsage);

            return this.taurusUsageMapper.fromTaurusUsage(savedTaurusUsage);
        }
        else
        {
            throw new RuntimeException("Account or Taurus not found");
        }

    }

    @Override
    public TaurusUsageDTO update(Long id, TaurusUsageDTO e) {
        Optional<TaurusUsage> existingTaurusUsage = taurusUsageRepository.findById(id);

        if (existingTaurusUsage.isPresent()) {
            TaurusUsage taurusUsage = existingTaurusUsage.get();
            taurusUsage.setUsageDate(e.getUsageDate());

            // Fetch Account and Taurus from their repositories
            Optional<Account> account = accountRepository.findById(e.getAccountId());
            Optional<Taurus> taurus = taurusRepository.findById(e.getTaurusId());

            if (account.isPresent() && taurus.isPresent()) {
                taurusUsage.setAccount(account.get());
                taurusUsage.setTaurus(taurus.get());

                TaurusUsage updatedTaurusUsage = taurusUsageRepository.save(taurusUsage);
                return taurusUsageMapper.fromTaurusUsage(updatedTaurusUsage);
            } else {
                throw new RuntimeException("Account or Taurus not found");
            }
        } else {
            throw new RuntimeException("TaurusUsage not found");
        }
    }

    // ***************** for remove*****************************

    @Override
    public void remove(Long id) {

        Optional<TaurusUsage> taurusUsage = this.taurusUsageRepository.findById(id);

        if (taurusUsage.isEmpty())
            throw new RuntimeException("Taurus usage with id : " +id+ " was not found");

        this.taurusUsageRepository.delete(taurusUsage.get());

    }

    @Transactional
    public void removeTaurusUsageByIdRange(Long startId, Long endId)
    {
        this.taurusUsageRepository.deleteByIdRange(startId, endId);
    }

    @Transactional
    public void removeTaurusUsageByChooseId(List<Long> listIdTaurusUsage)
    {
        this.taurusUsageRepository.deleteByIds(listIdTaurusUsage);
    }

    // ***************** *********************************************



    @Override
    public Optional<TaurusUsageDTO> getById(Long id) {
        return Optional.empty();
    }

    public Page<TaurusDTO> allTaurusByAccount(Long idAccount, Pageable pageable) {

/*        Optional<Account> account = this.accountRepository.findById(idAccount);

        if (account.isEmpty())
            throw new RuntimeException("Account with id : " + idAccount + " was not found");

        List<TaurusUsage> taurusUsageList = this.taurusUsageRepository.findByAccount(account.get());
        List<Taurus> taurusList = taurusUsageList.stream().map(TaurusUsage::getTaurus).collect(Collectors.toList());
        List<TaurusDTO> taurusDTOList = taurusList.stream().map(this.taurusMapper::fromTaurus).collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), taurusDTOList.size());
        int end = Math.min((start + pageable.getPageSize()), taurusDTOList.size());

        return new PageImpl<>(taurusDTOList.subList(start, end), pageable, taurusDTOList.size());*/
        return  null;
    }

    public Page<TaurusUsageDTO> getTaurusUsageByAccountId(Long idAccount, Pageable pageable) {

        Optional<Account> account = this.accountRepository.findById(idAccount);

        if (account.isEmpty())
            throw new RuntimeException("Account with id : " +idAccount+ " was not found");

        List<TaurusUsage> taurusUsages = this.taurusUsageRepository.findByAccount(account.get());

        List<TaurusUsageDTO> taurusUsageDTOS = taurusUsages.stream()
                .map(this.taurusUsageMapper::fromTaurusUsage)
                .collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), taurusUsageDTOS.size());
        int end = Math.min((start + pageable.getPageSize()), taurusUsageDTOS.size());

        List<TaurusUsageDTO> paginatedList = taurusUsageDTOS.subList(start, end);
        return new PageImpl<>(paginatedList, pageable, taurusUsageDTOS.size());
    }

    public List<TaurusUsageDTO> allTaurusUsageByWorkSessionId(String workSessionId)
    {
        return this.taurusUsageRepository.findByWorkSessionId(workSessionId)
                .stream()
                .map(this.taurusUsageMapper::fromTaurusUsage)
                .collect(Collectors.toList());
    }

    public List<String> getWorkSessionIdsByAccountId(Long idAccount)
    {
        return this.taurusUsageRepository.findDistinctWorkSessionIdsByAccountId(idAccount);
    }

    public Optional<TaurusUsageDTO> getTaurusUsageByTaurusId(Long taurusId) {

        Optional<TaurusUsage> taurusUsage = this.taurusUsageRepository.findByTaurusId(taurusId);

        if (taurusUsage.isEmpty())
            throw new RuntimeException("TaurusUsage with id : "+taurusId+ " was not found");

        return taurusUsage.map(this.taurusUsageMapper::fromTaurusUsage);
    }

    public Optional<TaurusUsageDTO> takeTaurusUsageByWorkSessionId(String workSessionId)
    {
        List<TaurusUsage> taurusUsages = this.taurusUsageRepository.findByWorkSessionId(workSessionId);

        if (taurusUsages.isEmpty())
            throw new RuntimeException("Taurus usage was not found");

        TaurusUsage taurusUsage = taurusUsages.get(0);

        return Optional.of(this.taurusUsageMapper.fromTaurusUsage(taurusUsage));
    }

    public Page<TaurusUsageDTO> searchTaurusUsages(String query, Pageable pageable) {
        List<TaurusUsage> filteredUsages = taurusUsageRepository.findAll().stream()
                .filter(taurusUsage ->
                        taurusUsage.getAccount().getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                                String.valueOf(taurusUsage.getTaurus().getTaurusNumber()).contains(query) ||
                                taurusUsage.getWorkSessionId().substring(taurusUsage.getWorkSessionId().length() - 4).contains(query)
                )
                .collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), filteredUsages.size());
        int end = Math.min((start + pageable.getPageSize()), filteredUsages.size());
        List<TaurusUsageDTO> paginatedList = filteredUsages.subList(start, end).stream()
                .map(taurusUsageMapper::fromTaurusUsage)
                .collect(Collectors.toList());

        return new PageImpl<>(paginatedList, pageable, filteredUsages.size());
    }





}
