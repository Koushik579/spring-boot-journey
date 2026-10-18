package practice_02._0._6.koushik.mapper;

import org.springframework.stereotype.Component;
import practice_02._0._6.koushik.dto.AuditResponseDTO;
import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.entity.AuditEntity;
import practice_02._0._6.koushik.entity.UserEntity;

@Component
public class Mapper {
    public UserResponseDTO entityToResponseDTO(UserEntity entity){
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setEmail(entity.getEmail());
        return responseDTO;
    }

    public AuditResponseDTO auditToDTO(AuditEntity auditEntity){
        AuditResponseDTO auditResponseDTO = new AuditResponseDTO();
        auditResponseDTO.setUserID(auditEntity.getUserID());
        auditResponseDTO.setName(auditEntity.getName());
        auditResponseDTO.setEmail(auditEntity.getEmail());
        auditResponseDTO.setDateTime(auditEntity.getDateTime());
        auditResponseDTO.setComment(auditEntity.getComment());
        auditResponseDTO.setStatus(auditEntity.getStatus());
        return auditResponseDTO;
    }
}
