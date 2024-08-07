package fr_scapartois_auto.chariot_inspector.account.dtos;

import fr_scapartois_auto.chariot_inspector.issue.dtos.IssueDTO;
import fr_scapartois_auto.chariot_inspector.pickup.dto.PickupDTO;
import fr_scapartois_auto.chariot_inspector.role.dtos.RoleDTO;
import fr_scapartois_auto.chariot_inspector.taurus_usage.dto.TaurusUsageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {

    private Long idAccount;

    private String name;

    private String firstName;

    private String email;

    private String password;

    private Long accountServiceBeanId;

    private String civility;

    private List<RoleDTO> roleDTOS;

    private List<TaurusUsageDTO> taurusUsageDTOS;

    private List<IssueDTO> issueDTOS;

    private List<PickupDTO> pickupDTOS;

}
