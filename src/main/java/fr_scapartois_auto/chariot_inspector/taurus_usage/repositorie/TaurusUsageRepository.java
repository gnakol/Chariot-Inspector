package fr_scapartois_auto.chariot_inspector.taurus_usage.repositorie;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.taurus_usage.bean.TaurusUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaurusUsageRepository extends JpaRepository<TaurusUsage, Long> {

    List<TaurusUsage> findByAccount(Account account);

    //List<TaurusUsage> findByAccountId(Long idAccount);

    List<TaurusUsage> findByWorkSessionId(String workSessionId);

    @Query("SELECT DISTINCT t.workSessionId FROM TaurusUsage t WHERE t.account.id = :accountId")
    List<String> findDistinctWorkSessionIdsByAccountId(@Param("accountId") Long accountId);

    // Delete by range id
    @Modifying
    @Query("DELETE FROM TaurusUsage t WHERE t.idTaurusUsage BETWEEN :startId AND :endId")
    void deleteByIdRange(@Param("startId") Long startId, @Param("endId") Long endId);

    // delete by choose id

    @Modifying
    @Query("DELETE FROM TaurusUsage t WHERE t.idTaurusUsage IN :ids")
    void deleteByIds(@Param("ids") List<Long> ids);

    @Query("SELECT t FROM TaurusUsage t WHERE t.taurus.idTaurus = :taurusId")
    Optional<TaurusUsage> findByTaurusId(@Param("taurusId") Long taurusId);




}
