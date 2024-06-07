package fr_scapartois_auto.chariot_inspector.uuid.services;

import fr_scapartois_auto.chariot_inspector.uuid.beans.Uuid;
import fr_scapartois_auto.chariot_inspector.uuid.repositories.UuidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UuidService {

    private final UuidRepository uuidRepository;

    public String generateUuid()
    {
        String generate = UUID.randomUUID().toString();

        if (this.uuidRepository.existsByValueUuid(generate))
        {
            return this.generateUuid();
        }
        else
        {
            Uuid uuid = new Uuid();

            uuid.setValueUuid(generate);
            this.uuidRepository.save(uuid);

            return generate;
        }
    }
}
