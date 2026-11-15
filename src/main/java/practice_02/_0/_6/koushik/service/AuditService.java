package practice_02._0._6.koushik.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import practice_02._0._6.koushik.dto.AuditResponseDTO;
import practice_02._0._6.koushik.entity.AuditEntity;
import practice_02._0._6.koushik.entity.UserEntity;
import practice_02._0._6.koushik.enums.AuditStatus;
import practice_02._0._6.koushik.exception.ResourceNotFoundException;
import practice_02._0._6.koushik.mapper.Mapper;
import practice_02._0._6.koushik.repository.AuditRepository;
import practice_02._0._6.koushik.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AuditService {

    private final AuditRepository auditRepository;
    private final Mapper mapper;
    private final UserRepository userRepository;

    public AuditService(AuditRepository auditRepository, Mapper mapper, UserRepository userRepository) {
        this.auditRepository = auditRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }
    private String dateAndTimeGenerator(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long saveLog(AuditEntity auditEntity, UserEntity userEntity, String comment){
        auditEntity.setUser(userEntity);
        auditEntity.setName(userEntity.getName());
        auditEntity.setEmail(userEntity.getEmail());
        auditEntity.setComment(comment);
        auditEntity.setDateTime(dateAndTimeGenerator());
        auditEntity.setStatus(AuditStatus.PENDING);
        AuditEntity auditId = auditRepository.save(auditEntity);
        return auditId.getId();
    }
    @Transactional
    public long newUserLogs(AuditEntity auditEntity, UserEntity userEntity){
        auditEntity.setComment("New User Register");
        auditEntity.setEmail(userEntity.getEmail());
        auditEntity.setName(userEntity.getName());
        auditEntity.setUser(userEntity);
        auditEntity.setDateTime(dateAndTimeGenerator());
        auditEntity.setStatus(AuditStatus.PENDING);
        AuditEntity auditId = auditRepository.save(auditEntity);
        return auditId.getId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateLogStatus(long id , AuditStatus status){
        int logStatusCode = auditRepository.updateStatus(status, id);
        if(logStatusCode == 0){
            throw new ResourceNotFoundException("Audit Record Not found with " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<AuditResponseDTO> getLogsByUser(long id){
        boolean active =
                TransactionSynchronizationManager.isActualTransactionActive();

        System.out.println("Transaction active: " + active);
        return auditRepository.findLogsById(id)
                .stream()
                .map(mapper :: auditToDTO)
                .toList();
    }
    @Transactional
    public long createAudit(long userId, String comment) {
        // 1. Find the existing user using userId
        // 2. If user does not exist, throw ResourceNotFoundException
        UserEntity userEntity = userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with this ID"));

        // 3. Create a new AuditEntity
        AuditEntity audit = new AuditEntity();
        // 4. Associate the existing UserEntity
        audit.setUser(userEntity);
        // 5. Set name and email from UserEntity
        audit.setName(userEntity.getName());
        audit.setEmail(userEntity.getEmail());
        // 6. Set comment
        audit.setComment(comment);
        // 7. Set current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        audit.setDateTime(formattedDateTime);
        // 8. Set status to AuditStatus.PENDING
        audit.setStatus(AuditStatus.PENDING);
        // 9. Save the audit
        AuditEntity savedAudit = auditRepository.save(audit);

        return savedAudit.getId();
    }
}
