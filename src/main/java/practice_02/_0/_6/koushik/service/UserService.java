package practice_02._0._6.koushik.service;

import org.springframework.transaction.annotation.Transactional;
import practice_02._0._6.koushik.dto.EmailUpdateRequest;
import practice_02._0._6.koushik.dto.NameUpdateRequest;
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
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final AuditService auditService;
    private final UserUpdateTransactionService transactionService;

    public UserService(UserRepository userRepository, Mapper mapper, AuditService auditService, UserUpdateTransactionService transactionService) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.auditService = auditService;
        this.transactionService = transactionService;
    }

    public List<UserResponseDTO> allUsers(){
        return userRepository.findAll()
                .stream()
                .map(mapper::entityToResponseDTO)
                .toList();
    }

    @Transactional
    public UserResponseDTO saveUser(UserEntity entity){
        UserEntity user = userRepository.save(entity);
        AuditEntity audit = new AuditEntity();
        long auditId = auditService.saveLog(audit, user,"New User Registered");
        try {
            auditService.updateLogStatus(auditId, AuditStatus.SUCCESS);
            return mapper.entityToResponseDTO(user);
        }
        catch (Exception e){
            auditService.updateLogStatus(auditId, AuditStatus.FAILURE);
            return mapper.entityToResponseDTO(user);
        }
    }

    public UserResponseDTO searchUserById(Long id){
        UserEntity entity = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No User Found with id: "+id));
        return mapper.entityToResponseDTO(entity);
    }

    @Transactional
    public UserResponseDTO updateUser(long id, NameUpdateRequest name){
        UserEntity entity = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No User Found with id: "+id));
        AuditEntity auditEntity = new AuditEntity();
        long auditID = auditService.saveLog(auditEntity, entity,
                "Name Update Attempt : " + name.getName());
        UserResponseDTO responseDTO = null;
        try {
            responseDTO = transactionService.nameUpdater(id, name.getName());
            auditService.updateLogStatus(auditID, AuditStatus.SUCCESS);
            System.out.println(auditService.getLogsByUser(id));
            return responseDTO;
        }
        catch (Exception e){
            auditService.updateLogStatus(auditID, AuditStatus.FAILURE);
            System.out.println(auditService.getLogsByUser(id));
            return responseDTO;
        }
    }

    @Transactional
    public UserResponseDTO updateEmail(long id, EmailUpdateRequest email){
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        AuditEntity auditEntity = new AuditEntity();
        long auditID = auditService.saveLog(auditEntity, entity,
                "Email Update Attempted : " + email.getEmail());
        UserResponseDTO responseDTO = null;
        try{
            responseDTO = transactionService.emailUpdater(id,email.getEmail());
            auditService.updateLogStatus(auditID, AuditStatus.SUCCESS);
            System.out.println(auditService.getLogsByUser(id));
            return responseDTO;
        }
        catch (Exception e){
            auditService.updateLogStatus(auditID, AuditStatus.FAILURE);
            System.out.println(auditService.getLogsByUser(id));
            return responseDTO;
        }
    }

    public void deleteUser(long id){
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        userRepository.deleteById(id);
    }

}
