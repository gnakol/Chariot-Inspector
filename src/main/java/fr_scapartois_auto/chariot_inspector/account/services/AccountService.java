package fr_scapartois_auto.chariot_inspector.account.services;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapperImpl;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.account_service.bean.AccountServiceBean;
import fr_scapartois_auto.chariot_inspector.account_service.repositorie.AccountServiceRepository;
import fr_scapartois_auto.chariot_inspector.role.beans.Role;
import fr_scapartois_auto.chariot_inspector.role.mappers.RoleMapper;
import fr_scapartois_auto.chariot_inspector.role.mappers.RoleMapperImpl;
import fr_scapartois_auto.chariot_inspector.role.repositories.RoleRepository;
import fr_scapartois_auto.chariot_inspector.role.services.RoleService;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements Webservices<AccountDTO>, UserDetailsService {

    private final AccountRepository accountRepository;

    private final  AccountMapper accountMapper = new AccountMapperImpl();

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper = new RoleMapperImpl();

    private final RoleService roleService;

    private final AccountServiceRepository accountServiceRepository;

    @Override
    public Page<AccountDTO> all(Pageable pageable) {
        return this.accountRepository.findAll(pageable)
                .map(this.accountMapper::fromAccount);
    }

    @Override
    public AccountDTO add(AccountDTO e) {
        return null;
    }

    public AccountDTO addNewAccount(AccountDTO accountDTO) {
        // Convertir le DTO en entité Account
        Account account = this.accountMapper.fromAccountDTO(accountDTO);

        Optional<AccountServiceBean> accountServiceBean = this.accountServiceRepository.findById(account.getAccountServiceBean().getIdAccountService());


        if (account.getRoles() == null || account.getRoles().isEmpty()) {
            // Si aucun rôle n'est fourni, utiliser les rôles par défaut
            account.setRoles(this.roleService.getDefaultRoles().stream()
                    .map(roleDTO -> this.roleRepository.findById(roleDTO.getIdRole()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        } else {

            List<Role> roles = account.getRoles().stream()
                    .map(role -> this.roleRepository.findById(role.getIdRole()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            account.setRoles(roles);
        }

        if (accountServiceBean.isPresent())
            account.setAccountServiceBean(accountServiceBean.get());
        else
            throw new RuntimeException("AccountServiceBean was not found");

        // Encoder le mot de passe
        account.setPassword(this.bCryptPasswordEncoder.encode(account.getPassword()));

        // Sauvegarder le compte
        Account accountSaved = this.accountRepository.save(account);

        // Retourner le DTO du compte sauvegardé
        return this.accountMapper.fromAccount(accountSaved);
    }


    @Override
    public AccountDTO update(Long id, AccountDTO e) {

        Account account = this.accountMapper.fromAccountDTO(e);

        if (id == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }

        return this.accountMapper.fromAccount(
                this.accountRepository.findById(id)
                        .map(a -> {
                            if (e.getName() != null)
                                a.setName(e.getName());
                            if (e.getFirstName() != null)
                                a.setFirstName(e.getFirstName());
                            if (e.getEmail() != null)
                                a.setEmail(e.getEmail());
                            if (e.getPassword() != null)
                                a.setPassword(this.bCryptPasswordEncoder.encode(e.getPassword()));
                            if (e.getAccountServiceBeanId() != null)
                            {
                                Optional<AccountServiceBean> accountServiceBean = this.accountServiceRepository.findById(account.getAccountServiceBean().getIdAccountService());
                                a.setAccountServiceBean(accountServiceBean.get());
                            }
                            if (e.getCivility() != null)
                                a.setCivility(e.getCivility());

                            if (e.getRoleDTOS() != null) {
                                List<Role> roles = e.getRoleDTOS().stream()
                                        .map(this.roleMapper::fromRoleDTO)
                                        .collect(Collectors.toList());
                                a.setRoles(roles);
                            }

                            return this.accountRepository.save(a);
                        })
                        .orElseThrow(() -> new RuntimeException("Account with id: " + id + " not found"))
        );
    }





    @Override
    public void remove(Long id) {

        Optional<Account> account = this.accountRepository.findById(id);

        if (account.isEmpty())
        {
            throw new RuntimeException("account with id :" +id+ "was not found");
        }

        this.accountRepository.delete(account.get());

    }

    @Override
    public Optional<AccountDTO> getById(Long id) {
        return this.accountRepository.findById(id)
                .map(this.accountMapper::fromAccount);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Long getAccountIdByEmail(String email)
    {
        Account account = this.accountRepository.findByEmail(email).get();

        return account.getIdAccount();
    }
}
