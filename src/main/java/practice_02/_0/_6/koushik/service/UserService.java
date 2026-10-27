package practice_02._0._6.koushik.service;

import jakarta.transaction.Transactional;
import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.entity.AuditEntity;
import practice_02._0._6.koushik.entity.UserEntity;
import org.springframework.stereotype.Service;
import practice_02._0._6.koushik.enums.AuditStatus;
import practice_02._0._6.koushik.exception.ResourceNotFoundException;
import practice_02._0._6.koushik.mapper.Mapper;
import practice_02._0._6.koushik.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final Mapper mapper;
    private final AuditService auditService;
    private final UserUpdateTransactionService transactionService;

    public UserService(UserRepository repository, Mapper mapper, AuditService auditService, UserUpdateTransactionService transactionService) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditService = auditService;
        this.transactionService = transactionService;
    }

    public List<UserResponseDTO> allUsers(){
        return repository.findAll()
                .stream()
                .map(mapper::entityToResponseDTO)
                .toList();
    }

    public UserResponseDTO saveUser(UserEntity entity){
        UserEntity user = repository.save(entity);
        return mapper.entityToResponseDTO(user);
    }

    public UserResponseDTO searchUserById(Long id){
        UserEntity entity = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No User Found with id: "+id));
        return mapper.entityToResponseDTO(entity);
    }

    @Transactional
    public UserResponseDTO updateUser(long id, String request){
        UserEntity entity = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No User Found with id: "+id));
        AuditEntity auditEntity = new AuditEntity();
        long auditID = auditService.saveLog(auditEntity, entity,
                "Name Update Attempt : " + request.getName());
        try {
            auditService.updateLogStatus(auditID, AuditStatus.SUCCESS);
            return mapper.entityToResponseDTO(entity);
        }
        catch (Exception e){
            auditService.updateLogStatus(auditID, AuditStatus.FAILURE);
            return mapper.entityToResponseDTO(entity);
        }
    }

    public UserResponseDTO updateEmail(long id, String email){
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        AuditEntity auditEntity = new AuditEntity();
        long auditID = auditService.saveLog(auditEntity, entity,
                "Email Update Attempted : " + email );
        try{
            UserResponseDTO responseDTO = transactionService.emailUpdater(id,email);
            auditService.updateLogStatus(auditID, AuditStatus.SUCCESS);
            return responseDTO;
        }
        catch (Exception e){
            auditService.updateLogStatus(auditID, AuditStatus.FAILURE);
            return mapper.entityToResponseDTO(entity);
        }
    }

    public void deleteUser(long id){
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        repository.deleteById(id);
    }

}
