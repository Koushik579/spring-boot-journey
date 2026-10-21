package practice_02._0._6.koushik.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import practice_02._0._6.koushik.dto.AuditResponseDTO;
import practice_02._0._6.koushik.entity.AuditEntity;
import practice_02._0._6.koushik.entity.UserEntity;
import practice_02._0._6.koushik.enums.AuditStatus;
import practice_02._0._6.koushik.exception.ResourceNotFoundException;
import practice_02._0._6.koushik.mapper.Mapper;
import practice_02._0._6.koushik.repository.AuditRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AuditService {

    private final AuditRepository auditRepository;
    private final Mapper mapper;

    public AuditService(AuditRepository auditRepository, Mapper mapper) {
        this.auditRepository = auditRepository;
        this.mapper = mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long updateLog(AuditEntity entity, UserEntity userEntity, String comment){
        entity.setUserID(userEntity.getId());
        entity.setName(userEntity.getName());
        entity.setEmail(userEntity.getEmail());
        entity.setComment(comment);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        entity.setDateTime(formattedDateTime);
        entity.setStatus(AuditStatus.PENDING);
        auditRepository.save(entity);
        return entity.getId();
    }

    public List<AuditResponseDTO> getLogsByUser(long id){
        return auditRepository.findLogsById(id)
                .stream()
                .map(mapper :: auditToDTO)
                .toList();
    }

    public void updateLogStatus(long id , AuditStatus status){
        int logStatusCode = auditRepository.updateStatus(status, id);
        if(logStatusCode == 0){
            throw new ResourceNotFoundException("Audit Record Not found with " + id);
        }
    }
}
