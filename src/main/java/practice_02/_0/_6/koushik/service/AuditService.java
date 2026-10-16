package practice_02._0._6.koushik.service;

import org.springframework.stereotype.Service;
import practice_02._0._6.koushik.dto.AuditResponseDTO;
import practice_02._0._6.koushik.entity.AuditEntity;
import practice_02._0._6.koushik.entity.UserEntity;
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

    public void nameUpdateLog(AuditEntity entity, UserEntity userEntity, String comment){
        entity.setUserID(userEntity.getId());
        entity.setName(userEntity.getName());
        entity.setEmail(userEntity.getEmail());
        entity.setComment(comment);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        entity.setDateTime(formattedDateTime);
        auditRepository.save(entity);
        mapper.auditToDTO(entity);

    }

    public List<AuditResponseDTO> getLogsByUser(long id){
        return auditRepository.findLogsById(id)
                .stream()
                .map(mapper :: auditToDTO)
                .toList();

    }
}
