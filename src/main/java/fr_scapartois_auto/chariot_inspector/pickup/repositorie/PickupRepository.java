package fr_scapartois_auto.chariot_inspector.pickup.repositorie;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.pickup.bean.Pickup;
import fr_scapartois_auto.chariot_inspector.taurus_usage.bean.TaurusUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PickupRepository extends JpaRepository<Pickup, Long> {

    List<Pickup> findByAccount(Account account);

    List<Pickup> findByWorkSessionId(String workSessionId);

    List<Pickup> findByCart(Cart cart);

    @Query("SELECT DISTINCT p.workSessionId FROM Pickup p WHERE p.account.id = :accountId")
    List<String> findDistinctWorkSessionIdsByAccountId(@Param("accountId") Long accountId);

    // Delete by range id
    @Modifying
    @Query("DELETE FROM Pickup p WHERE p.idPickup BETWEEN :startId AND :endId")
    void deleteByIdRange(@Param("startId") Long startId, @Param("endId") Long endId);

    // delete by choose id

    @Modifying
    @Query("DELETE FROM Pickup p WHERE p.idPickup IN :ids")
    void deleteByIds(@Param("ids") List<Long> ids);

    @Query("SELECT p.id FROM Pickup p WHERE p.cart.cartNumber = :cartNumber")
    List<Long> findIdByCartNumber(@Param("cartNumber") String cartNumber);
}
