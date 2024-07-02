package fr_scapartois_auto.chariot_inspector.taurus_usage.repositorie;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.taurus_usage.bean.TaurusUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaurusUsageRepository extends JpaRepository<TaurusUsage, Long> {

    List<TaurusUsage> findByAccount(Account account);

}
