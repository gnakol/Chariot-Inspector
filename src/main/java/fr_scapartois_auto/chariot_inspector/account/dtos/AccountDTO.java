package fr_scapartois_auto.chariot_inspector.account.dtos;

import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.role.dtos.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {

    private Long idAccount;
    private String refAccount;
    private String name;
    private String firstName;
    private String email;
    private String password;
    private String service;
    private String civility;
    private Long taurusNumber;
    private Date pickUpDateTime;

    private CartDTO cartDTO;

    private List<RoleDTO> roleDTOS;

}
