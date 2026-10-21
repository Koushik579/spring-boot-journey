package practice_02._0._6.koushik.service;

import jakarta.transaction.Transactional;
import practice_02._0._6.koushik.dto.UpdateNameRequest;
import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.entity.AuditEntity;
import practice_02._0._6.koushik.entity.UserEntity;
import org.springframework.stereotype.Service;
import practice_02._0._6.koushik.enums.AuditStatus;
import practice_02._0._6.koushik.exception.ResourceNotFoundException;
import practice_02._0._6.koushik.mapper.Mapper;
import practice_02._0._6.koushik.repository.AuditRepository;
import practice_02._0._6.koushik.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final Mapper mapper;
    private final AuditService auditService;
    private final AuditRepository auditRepository;

    public UserService(UserRepository repository, Mapper mapper, AuditService auditService, AuditRepository auditRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.auditService = auditService;
        this.auditRepository = auditRepository;
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
        UserEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        return mapper.entityToResponseDTO(entity);
    }

    @Transactional
    public UserResponseDTO updateUser(long id, UpdateNameRequest request){
        UserEntity entity = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No User Found with id: "+id));
        entity.setName(request.getName());
        AuditEntity auditEntity = new AuditEntity();
        long auditID = auditService.updateLog(auditEntity, entity, "Name Update Attempt : " + request.getName());
        try {
            auditService.updateLogStatus(auditID, AuditStatus.SUCCESS);
            //UserEntity savedEntity = repository.save(entity);//@Transactional dirty check helps here no problem if this is commented
            return mapper.entityToResponseDTO(entity);
        }
        catch (Exception e){
            auditService.updateLogStatus(auditID, AuditStatus.FAILURE);
            return mapper.entityToResponseDTO(entity);
        }
    }

    @Transactional
    public UserResponseDTO updateEmail(long id, String email){
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        entity.setEmail(email);
        AuditEntity auditEntity = new AuditEntity();
        long auditID = auditService.updateLog(auditEntity, entity, "Email Update Attempted : " + email );
        //UserEntity savedEntity = repository.save(entity);

        try{
            //throw new RuntimeException("Testing RollBack");
            auditService.updateLogStatus(auditID, AuditStatus.SUCCESS);
            return mapper.entityToResponseDTO(entity);
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
