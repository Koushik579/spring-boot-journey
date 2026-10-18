package practice_02._0._6.koushik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice_02._0._6.koushik.entity.AuditEntity;
import practice_02._0._6.koushik.enums.AuditStatus;

import java.util.List;

public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
    @Query("SELECT l FROM AuditEntity l WHERE l.userID = :id")
    List<AuditEntity> findLogsById(@Param("id") long id);

    //Audit Failure Status Update
    @Modifying
    @Query("UPDATE AuditEntity l SET l.status = :status WHERE l.id = :id")
    void updateStatus(@Param("status") AuditStatus status,@Param("id") long id );

}
