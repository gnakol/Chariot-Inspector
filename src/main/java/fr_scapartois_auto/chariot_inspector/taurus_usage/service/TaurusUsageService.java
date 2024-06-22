package fr_scapartois_auto.chariot_inspector.taurus_usage.service;

import fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.bean.Taurus;
import fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.mapper.TaurusMapper;
import fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.mapper.TaurusMapperImpl;
import fr_scapartois_auto.chariot_inspector.accompanied.beans.taurus.repositorie.TaurusRepository;
import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapperImpl;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.taurus_usage.bean.TaurusUsage;
import fr_scapartois_auto.chariot_inspector.taurus_usage.dto.TaurusUsageDTO;
import fr_scapartois_auto.chariot_inspector.taurus_usage.mapper.TaurusUsageMapper;
import fr_scapartois_auto.chariot_inspector.taurus_usage.mapper.TaurusUsageMapperImpl;
import fr_scapartois_auto.chariot_inspector.taurus_usage.repositorie.TaurusUsageRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaurusUsageService implements Webservices<TaurusUsageDTO> {

    private final AccountRepository accountRepository;

    private final TaurusRepository taurusRepository;

    private final TaurusUsageRepository taurusUsageRepository;

    private final AccountMapper accountMapper = new AccountMapperImpl();

    private final TaurusMapper taurusMapper = new TaurusMapperImpl();

    private final TaurusUsageMapper taurusUsageMapper = new TaurusUsageMapperImpl();


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
            taurusUsage.setAccount(account.get());
            taurusUsage.setTaurus(taurus.get());

            TaurusUsage savedTaurusUsage = this.taurusUsageRepository.save(taurusUsage);

            return this.taurusUsageMapper.fromTaurusUsage(taurusUsage);
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

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<TaurusUsageDTO> getById(Long id) {
        return Optional.empty();
    }
}
