package fr_scapartois_auto.chariot_inspector.account.services;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.dtos.AccountDTO;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapper;
import fr_scapartois_auto.chariot_inspector.account.mappers.AccountMapperImpl;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapper;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapperImpl;
import fr_scapartois_auto.chariot_inspector.cart.repositories.CartRepository;
import fr_scapartois_auto.chariot_inspector.role.beans.Role;
import fr_scapartois_auto.chariot_inspector.role.mappers.RoleMapper;
import fr_scapartois_auto.chariot_inspector.role.mappers.RoleMapperImpl;
import fr_scapartois_auto.chariot_inspector.role.repositories.RoleRepository;
import fr_scapartois_auto.chariot_inspector.role.services.RoleService;
import fr_scapartois_auto.chariot_inspector.uuid.services.UuidService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements Webservices<AccountDTO>, UserDetailsService {

    private final AccountRepository accountRepository;

    private final  AccountMapper accountMapper = new AccountMapperImpl();

    private final UuidService uuidService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper = new RoleMapperImpl();

    private final RoleService roleService;

    private final CartMapper cartMapper = new CartMapperImpl();

    private final CartRepository cartRepository;

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
        accountDTO.setPassword(this.bCryptPasswordEncoder.encode(accountDTO.getPassword()));
        accountDTO.setRoleDTOS(this.roleService.getDefaultRoles());

        return this.accountMapper.fromAccount(this.accountRepository.save(this.accountMapper.fromAccountDTO(accountDTO)));
    }

    @Override
    public AccountDTO update(Long id, AccountDTO e) {
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
                            if (e.getService() != null)
                                a.setService(e.getService());

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
