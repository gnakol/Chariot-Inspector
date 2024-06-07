package fr_scapartois_auto.chariot_inspector.account.services;

import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapperImpl;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.role.mappers.RoleMapper;
import fr_scapartois_auto.chariot_inspector.role.mappers.RoleMapperImpl;
import fr_scapartois_auto.chariot_inspector.role.repositories.RoleRepository;
import fr_scapartois_auto.chariot_inspector.role.services.RoleService;
import fr_scapartois_auto.chariot_inspector.uuid.services.UuidService;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements Webservices<AccountDTO>, UserDetailsService {

    private final AccountRepository accountRepository;

    private final  AccountMapper accountMapper = new AccountMapperImpl();

    private final UuidService uuidService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper = new RoleMapperImpl();

    private final RoleService roleService;

    @Override
    public Page<AccountDTO> all(Pageable pageable) {
        return this.accountRepository.findAll(pageable)
                .map(this.accountMapper::fromAccount);
    }

    @Override
    public AccountDTO add(AccountDTO e) {
        return null;
    }

    public AccountDTO addNewAccount(AccountDTO accountDTO)
    {
        accountDTO.setRefAccount(this.uuidService.generateUuid());
        accountDTO.setPassword(this.bCryptPasswordEncoder.encode(accountDTO.getPassword()));
        accountDTO.setRoleDTOS(this.roleService.getDefaultRoles());

        return this.accountMapper.fromAccount(this.accountRepository.save(this.accountMapper.fromAccountDTO(accountDTO)));
    }

    @Override
    public AccountDTO update(Long id, AccountDTO e) {
        return this.accountMapper.fromAccount(
                this.accountRepository.findById(id)
                        .map(a -> {
                            if (a.getName() != null)
                                a.setName(e.getName());
                            if (a.getFirstName() != null)
                                a.setFirstName(e.getFirstName());
                            if (a.getPassword() != null)
                                a.setPassword(this.bCryptPasswordEncoder.encode(e.getPassword()));
                            if (a.getService() != null)
                                a.setService(e.getService());
                            if (a.getTaurusNumber() != null)
                                a.setTaurusNumber(e.getTaurusNumber());
                            if (a.getPickUpDateTime() != null)
                                a.setPickUpDateTime(e.getPickUpDateTime());

                            return this.accountRepository.save(a);
                        })
                        .orElseThrow(() -> new RuntimeException("Account with id: " +id+ " not found"))
        );
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<AccountDTO> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
