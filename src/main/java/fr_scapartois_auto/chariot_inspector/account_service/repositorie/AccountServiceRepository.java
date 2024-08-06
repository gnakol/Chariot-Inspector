package fr_scapartois_auto.chariot_inspector.account_service.repositorie;

import fr_scapartois_auto.chariot_inspector.account_service.bean.AccountServiceBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountServiceRepository extends JpaRepository<AccountServiceBean, Long>{

    Optional<AccountServiceBean> findByName(String name);
}
