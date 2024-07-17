package fr_scapartois_auto.chariot_inspector.historical.bean;

import fr_scapartois_auto.chariot_inspector.battery.dtos.BatteryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryEntryDTO {

    private String name;

    private String firstName;

    private Long taurusNumber;

    private String issueDescription;

    private String cartNumber;

    private List<BatteryDTO> batteryDTOS;

    private LocalDate usageDate;

    private String teamName;

    private String shiftName;
}
