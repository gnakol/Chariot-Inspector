package fr_scapartois_auto.chariot_inspector.session.bean;

import lombok.Data;

import java.util.UUID;

@Data
public class WorkSessionUtil {

    public static String generateWorkSessionId()
    {
        return UUID.randomUUID().toString();
    }
}
