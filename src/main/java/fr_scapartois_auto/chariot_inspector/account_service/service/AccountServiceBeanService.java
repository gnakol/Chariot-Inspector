package fr_scapartois_auto.chariot_inspector.account_service.service;

import fr_scapartois_auto.chariot_inspector.account_service.bean.AccountServiceBean;
import fr_scapartois_auto.chariot_inspector.account_service.dto.AccountServiceDTO;
import fr_scapartois_auto.chariot_inspector.account_service.mapper.AccountServiceMapper;
import fr_scapartois_auto.chariot_inspector.account_service.mapper.AccountServiceMapperImpl;
import fr_scapartois_auto.chariot_inspector.account_service.repositorie.AccountServiceRepository;
import fr_scapartois_auto.chariot_inspector.ware_house.bean.WareHouse;
import fr_scapartois_auto.chariot_inspector.ware_house.repositorie.WareHouseRepository;
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

    private final WareHouseRepository wareHouseRepository;


    @Override
    public Page<AccountServiceDTO> all(Pageable pageable) {
        return this.accountServiceRepository.findAll(pageable)
                .map(this.accountServiceMapper::fromAccountService);
    }

    @Override
    public AccountServiceDTO add(AccountServiceDTO e) {

        AccountServiceBean accountServiceBean = this.accountServiceMapper.fromAccountServiceDTO(e);

        Optional<WareHouse> wareHouse = this.wareHouseRepository.findById(accountServiceBean.getWareHouse().getIdWareHouse());

        if (wareHouse.isPresent())
        {
            accountServiceBean.setWareHouse(wareHouse.get());

            AccountServiceBean savedAccountServiceBean = this.accountServiceRepository.save(accountServiceBean);

            return this.accountServiceMapper.fromAccountService(savedAccountServiceBean);
        }
        else
            throw new RuntimeException("Ware House was not found");
    }

    @Override
    public AccountServiceDTO update(Long id, AccountServiceDTO e) {

        AccountServiceBean serviceBean = this.accountServiceMapper.fromAccountServiceDTO(e);

        return this.accountServiceMapper.fromAccountService(this.accountServiceRepository.findById(id)
                .map(accountServiceBean -> {
                    if (e.getName() != null)
                        accountServiceBean.setName(e.getName());

                    if (e.getWareHouseId() != null)
                    {
                        Optional<WareHouse> wareHouse = this.wareHouseRepository.findById(serviceBean.getWareHouse().getIdWareHouse());
                        accountServiceBean.setWareHouse(wareHouse.get());
                    }

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

    public Long getIdWareHouseByName(String wareHouseName)
    {
        Optional<AccountServiceBean> accountServiceBean = this.accountServiceRepository.findByName(wareHouseName);

        if (accountServiceBean.isEmpty())
            throw new RuntimeException("Account service bean with name : "+wareHouseName+ " was not found");

        return accountServiceBean.get().getIdAccountService();
    }
}
