package fr_scapartois_auto.chariot_inspector.cart_category.repositorie;

import fr_scapartois_auto.chariot_inspector.cart_category.bean.CartCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartCategoryRepository extends JpaRepository<CartCategory, Long> {
}
