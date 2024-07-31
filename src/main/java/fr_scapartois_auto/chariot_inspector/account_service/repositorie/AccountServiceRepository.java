package fr_scapartois_auto.chariot_inspector.account_service.repositorie;

import fr_scapartois_auto.chariot_inspector.account_service.bean.AccountServiceBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountServiceRepository extends JpaRepository<AccountServiceBean, Long>{
}
