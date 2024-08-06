package fr_scapartois_auto.chariot_inspector.account_service.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountServiceDTO {

    private Long idAccountService;

    private String name;

    private Long wareHouseId;
}
