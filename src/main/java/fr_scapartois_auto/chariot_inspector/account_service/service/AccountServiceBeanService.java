package fr_scapartois_auto.chariot_inspector.account_service.service;

import fr_scapartois_auto.chariot_inspector.account_service.bean.AccountServiceBean;
import fr_scapartois_auto.chariot_inspector.account_service.dto.AccountServiceDTO;
import fr_scapartois_auto.chariot_inspector.account_service.mapper.AccountServiceMapper;
import fr_scapartois_auto.chariot_inspector.account_service.mapper.AccountServiceMapperImpl;
import fr_scapartois_auto.chariot_inspector.account_service.repositorie.AccountServiceRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceBeanService implements Webservices<AccountServiceDTO> {

    private final AccountServiceRepository accountServiceRepository;

    private final AccountServiceMapper accountServiceMapper = new AccountServiceMapperImpl();


    @Override
    public Page<AccountServiceDTO> all(Pageable pageable) {
        return this.accountServiceRepository.findAll(pageable)
                .map(this.accountServiceMapper::fromAccountService);
    }

    @Override
    public AccountServiceDTO add(AccountServiceDTO e) {
        return this.accountServiceMapper.fromAccountService(this.accountServiceRepository.save(this.accountServiceMapper.fromAccountServiceDTO(e)));
    }

    @Override
    public AccountServiceDTO update(Long id, AccountServiceDTO e) {
        return this.accountServiceMapper.fromAccountService(this.accountServiceRepository.findById(id)
                .map(accountServiceBean -> {
                    if (e.getName() != null)
                        accountServiceBean.setName(e.getName());

                    return this.accountServiceRepository.save(accountServiceBean);
                })
                .orElseThrow(() -> new RuntimeException("Sorry this id account service was not found")));
    }

    @Override
    public void remove(Long id) {

        Optional<AccountServiceBean> accountServiceBean = this.accountServiceRepository.findById(id);

        if (accountServiceBean.isEmpty())
            throw new RuntimeException("Account service with id :" +id+ " was not found");

        this.accountServiceRepository.delete(accountServiceBean.get());

    }

    @Override
    public Optional<AccountServiceDTO> getById(Long id) {
        return this.accountServiceRepository.findById(id)
                .map(this.accountServiceMapper::fromAccountService);
    }
}
