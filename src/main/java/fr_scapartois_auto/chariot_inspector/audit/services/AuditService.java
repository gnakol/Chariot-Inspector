package fr_scapartois_auto.chariot_inspector.audit.services;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.audit.beans.Audit;
import fr_scapartois_auto.chariot_inspector.audit.dtos.AuditDTO;
import fr_scapartois_auto.chariot_inspector.audit.mappers.AuditMapper;
import fr_scapartois_auto.chariot_inspector.audit.mappers.AuditMapperImpl;
import fr_scapartois_auto.chariot_inspector.audit.repositories.AuditRepository;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.cart.repositories.CartRepository;
import fr_scapartois_auto.chariot_inspector.session.service.WorkSessionService;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditService implements Webservices<AuditDTO> {

    private final AuditRepository auditRepository;

    private final AuditMapper auditMapper = new AuditMapperImpl();

    private final AccountRepository accountRepository;

    private final CartRepository cartRepository;

    private final WorkSessionService workSessionService;


    @Override
    public Page<AuditDTO> all(Pageable pageable) {
        return this.auditRepository.findAll(pageable)
                .map(this.auditMapper::fromAudit);
    }

    @Override
    public AuditDTO add(AuditDTO e) {

        Audit audit = this.auditMapper.fromAuditDTO(e);

        Optional<Account> account = this.accountRepository.findById(audit.getAccount().getIdAccount());
        Optional<Cart> cart = this.cartRepository.findById(audit.getCart().getIdCart());

        if (account.isPresent() && cart.isPresent())
        {
            String workSessionId = this.workSessionService.getActiveWorkSession(account.get().getIdAccount())
                    .orElseThrow(() -> new RuntimeException("No active work session found"))
                    .getWorkSessionId();

            audit.setAccount(account.get());
            audit.setCart(cart.get());
            audit.setWorkSessionId(workSessionId);

            Audit auditSaved = this.auditRepository.save(audit);

            return this.auditMapper.fromAudit(auditSaved);
        }
        else
        {
            throw new RuntimeException("Account and Cart was not found");
        }
    }

    @Override
    public AuditDTO update(Long id, AuditDTO e) {

        Audit auditEntity = this.auditMapper.fromAuditDTO(e);


        return this.auditMapper.fromAudit(this.auditRepository.findById(id)
                .map(audit -> {
                    if (e.getGoodPoints() != null)
                        audit.setGoodPoints(e.getGoodPoints());
                    if (e.getImprovementAreas() != null)
                        audit.setImprovementAreas(e.getImprovementAreas());
                    if (e.getAuditTime() != null)
                        audit.setAuditTime(e.getAuditTime());
                    if (e.getStatus() != null)
                        audit.setStatus(e.getStatus());
                    if (e.getFindings() != null)
                        audit.setFindings(e.getFindings());
                    if (e.getAccountId() != null)
                    {
                        Optional<Account> account = this.accountRepository.findById(auditEntity.getAccount().getIdAccount());
                        if (account.isPresent())
                            audit.setAccount(account.get());
                    }
                    if (e.getCartId() != null)
                    {
                        Optional<Cart> cart = this.cartRepository.findById(auditEntity.getCart().getIdCart());
                        if (cart.isPresent())
                            audit.setCart(cart.get());
                    }

                    return  this.auditRepository.save(audit);
                })
                .orElseThrow(() -> new RuntimeException("Audit with id : " +id+ " was not found")));
    }

    @Override
    public void remove(Long id) {

        Optional<Audit> audit = this.auditRepository.findById(id);

        if (audit.isEmpty())
            throw new RuntimeException("Audit with id : " +id+ " was not found");

        this.auditRepository.delete(audit.get());

    }

    @Override
    public Optional<AuditDTO> getById(Long id) {
        return this.auditRepository.findById(id)
                .map(this.auditMapper::fromAudit);
    }
}
