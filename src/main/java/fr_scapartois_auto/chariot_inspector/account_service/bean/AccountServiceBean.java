package fr_scapartois_auto.chariot_inspector.account_service.bean;

import fr_scapartois_auto.chariot_inspector.ware_house.bean.WareHouse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "service")
public class AccountServiceBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service")
    private Long idAccountService;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_ware_house")
    private WareHouse wareHouse;
}
