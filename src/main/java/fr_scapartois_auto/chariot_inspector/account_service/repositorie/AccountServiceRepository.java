package fr_scapartois_auto.chariot_inspector.account_service.repositorie;

import fr_scapartois_auto.chariot_inspector.account_service.bean.AccountServiceBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountServiceRepository extends JpaRepository<AccountServiceBean, Long>{

    Optional<AccountServiceBean> findByName(String name);

    @Query("SELECT a FROM AccountServiceBean a WHERE a.wareHouse.idWareHouse = :warehouseId")
    Page<AccountServiceBean> findByWareHouseId(@Param("warehouseId") Long warehouseId, Pageable pageable);
}
