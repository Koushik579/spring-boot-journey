package practice_02._0._6.koushik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice_02._0._6.koushik.entity.AuditEntity;

import java.util.List;

public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
    @Query("SELECT l FROM AuditEntity l where l.userID = :id")
    List<AuditEntity> findLogsById(@Param("id") long id);
}
