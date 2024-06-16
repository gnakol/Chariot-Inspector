package fr_scapartois_auto.chariot_inspector.action_carried_out.services;

import fr_scapartois_auto.chariot_inspector.action_carried_out.beans.ActionCarriedOut;
import fr_scapartois_auto.chariot_inspector.action_carried_out.repositories.ActionCarriedOutRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActionCarriedOutServe implements Webservices<ActionCarriedOut> {

    private final ActionCarriedOutRepository actionCarriedOutRepository;

    @Override
    public Page<ActionCarriedOut> all(Pageable pageable) {
        return this.actionCarriedOutRepository.findAll(pageable);
    }

    @Override
    public ActionCarriedOut add(ActionCarriedOut e) {
        return this.actionCarriedOutRepository.save(e);
    }

    @Override
    public ActionCarriedOut update(Long id, ActionCarriedOut e) {
        return this.actionCarriedOutRepository.findById(id)
                .map(actionCarriedOut -> {
                    if (e.getDescription() != null)
                        actionCarriedOut.setDescription(e.getDescription());
                    if (e.getIssue() != null)
                    {
                        actionCarriedOut.setAccount(e.getAccount());
                    }

                    if (e.getIssue() != null)
                    {
                        actionCarriedOut.setIssue(e.getIssue());
                    }

                    return this.actionCarriedOutRepository.save(actionCarriedOut);
                })
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public void remove(Long id) {

        Optional<ActionCarriedOut> actionCarriedOut = this.actionCarriedOutRepository.findById(id);

        if (actionCarriedOut.isEmpty())
        {
            throw  new RuntimeException("not found");
        }

        this.actionCarriedOutRepository.delete(actionCarriedOut.get());

    }

    @Override
    public Optional<ActionCarriedOut> getById(Long id) {
        return this.actionCarriedOutRepository.findById(id)
                .map(Optional::of)
                .orElseThrow(() -> new RuntimeException("not found"));
    }
}
