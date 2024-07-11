package fr_scapartois_auto.chariot_inspector.session.service;

import fr_scapartois_auto.chariot_inspector.exception.other.ResourceNotFoundException;
import fr_scapartois_auto.chariot_inspector.session.bean.WorkSession;
import fr_scapartois_auto.chariot_inspector.session.bean.WorkSessionUtil;
import fr_scapartois_auto.chariot_inspector.session.repositorie.WorkSessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkSessionService {

    private final WorkSessionRepository workSessionRepository;

    public Page<WorkSession> allWorkSession(Pageable pageable)
    {
        return this.workSessionRepository.findAll(pageable);
    }

    public String startNewWorkSession(Long accountId)
    {
        // Close the all active session for this user

        List<WorkSession> activeWorkSessions = this.workSessionRepository.findActiveWorkSessionsByAccountId(accountId);
        for (WorkSession activeSession : activeWorkSessions)
        {
            activeSession.setEndTime(LocalDateTime.now());
            this.workSessionRepository.save(activeSession);
        }

        // Create a new work session

        String workSessionId = WorkSessionUtil.generateWorkSessionId();

        WorkSession workSession = new WorkSession();

        workSession.setAccountId(accountId);
        workSession.setWorkSessionId(workSessionId);
        workSession.setStartTime(LocalDateTime.now());

        this.workSessionRepository.save(workSession);

        return workSessionId;
    }

    public Optional<WorkSession> getActiveWorkSession(Long accountId) {
        List<WorkSession> activeWorkSessions = this.workSessionRepository.findActiveWorkSessionsByAccountId(accountId);

        if (activeWorkSessions.size() > 1) {
            throw new RuntimeException("Multiple active work sessions found for accountId: " + accountId);
        }

        return activeWorkSessions.isEmpty() ? Optional.empty() : Optional.of(activeWorkSessions.get(0));
    }

    public void endWorkSession(String workSessionId)
    {
        WorkSession workSession = this.workSessionRepository.findByWorkSessionId(workSessionId)
                .orElseThrow(()-> new ResourceNotFoundException("Work session not found"));
        workSession.setEndTime(LocalDateTime.now());

        this.workSessionRepository.save(workSession);
    }

    public void removeSession(Long idWorkSession)
    {
        Optional<WorkSession> workSession = this.workSessionRepository.findById(idWorkSession);

        if (workSession.isEmpty())
            throw new RuntimeException(("Work Session with id : "+idWorkSession+ " was not found"));

        this.workSessionRepository.delete(workSession.get());
    }

    @Transactional
    public void removeWorkSessionByIdRange(Long startId, Long endId)
    {
        this.workSessionRepository.deleteByIdRange(startId, endId);
    }

    @Transactional
    public void removeWorkSessionByChooseId(List<Long> listIdWorkSession)
    {
        this.workSessionRepository.deleteByIds(listIdWorkSession);
    }


}
