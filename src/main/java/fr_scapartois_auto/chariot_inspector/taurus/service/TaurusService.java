package fr_scapartois_auto.chariot_inspector.taurus.service;

import fr_scapartois_auto.chariot_inspector.taurus.bean.Taurus;
import fr_scapartois_auto.chariot_inspector.taurus.dto.TaurusDTO;
import fr_scapartois_auto.chariot_inspector.taurus.mapper.TaurusMapper;
import fr_scapartois_auto.chariot_inspector.taurus.mapper.TaurusMapperImpl;
import fr_scapartois_auto.chariot_inspector.taurus.repositorie.TaurusRepository;
import fr_scapartois_auto.chariot_inspector.uuid.services.UuidService;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaurusService implements Webservices<TaurusDTO> {

    private final TaurusRepository taurusRepository;

    private final TaurusMapper taurusMapper = new TaurusMapperImpl();

    private final UuidService uuidService;


    @Override
    public Page<TaurusDTO> all(Pageable pageable) {
        return this.taurusRepository.findAll(pageable)
                .map(this.taurusMapper::fromTaurus);
    }

    @Override
    public TaurusDTO add(TaurusDTO e) {

        e.setRefTaurus(this.uuidService.generateUuid());

        return this.taurusMapper.fromTaurus(this.taurusRepository.save(this.taurusMapper.fromTaurusDTO(e)));
    }

    @Override
    public TaurusDTO update(Long id, TaurusDTO e) {
        return this.taurusMapper.fromTaurus(this.taurusRepository.findById(id)
                .map(taurus -> {
                    if (e.getTaurusNumber() != null)
                        taurus.setTaurusNumber(e.getTaurusNumber());

                    return this.taurusRepository.save(taurus);
                })
                .orElseThrow(() -> new RuntimeException("taurus id with not found")));
    }

    @Override
    public void remove(Long id) {

        Optional<Taurus> taurus = this.taurusRepository.findById(id);

        if (taurus.isEmpty())
            throw new RuntimeException("not found");

        this.taurusRepository.delete(taurus.get());

    }

    @Override
    public Optional<TaurusDTO> getById(Long id) {
        return this.taurusRepository.findById(id)
                .map(this.taurusMapper::fromTaurus);
    }

    public Long getTaurusIdByNumber(Long number)
    {
        Optional<Taurus> taurus = this.taurusRepository.findByTaurusNumber(number);

        if (taurus.isEmpty())
        {
            throw new RuntimeException("taurus with number : " +number+ " was not found");
        }

        return taurus.get().getIdTaurus();
    }
}
