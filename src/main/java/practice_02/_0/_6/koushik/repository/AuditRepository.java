package practice_02._0._6.koushik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice_02._0._6.koushik.entity.AuditEntity;

public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
}
