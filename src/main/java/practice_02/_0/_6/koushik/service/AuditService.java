package practice_02._0._6.koushik.service;

import org.springframework.stereotype.Service;
import practice_02._0._6.koushik.repository.AuditRepository;

@Service
public class AuditService {

    private final AuditRepository auditRepository;


    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public String nameUpdateLog(String name){
        auditRepository.save()
    }
}
