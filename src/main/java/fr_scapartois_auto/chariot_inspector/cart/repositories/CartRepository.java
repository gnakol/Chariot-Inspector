package fr_scapartois_auto.chariot_inspector.cart.repositories;

import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findTopByOrderByIdCartDesc();

    Optional<Cart> findByCartNumber(String cartNumber);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.idCart BETWEEN :startId AND :endId")
    void deleteByIdRange(@Param("startId") Long startId, @Param("endId") Long endId);

    @Modifying
    @Query("delete from Cart c where c.idCart in :ids")
    void deleteByIds(@Param("ids")List<Long> ids);
}
