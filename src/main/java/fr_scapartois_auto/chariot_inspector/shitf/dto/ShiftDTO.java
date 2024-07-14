package fr_scapartois_auto.chariot_inspector.shitf.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ShiftDTO {

    private Long idShift;

    private String name;

    private LocalTime startTime;

    private LocalTime endTime;
}
