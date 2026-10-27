package practice_02._0._6.koushik.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.entity.UserEntity;
import practice_02._0._6.koushik.exception.ResourceNotFoundException;
import practice_02._0._6.koushik.mapper.Mapper;
import practice_02._0._6.koushik.repository.UserRepository;

@Service
public class UserUpdateTransactionService {

    private final UserRepository repository;
    private final Mapper mapper;

    public UserUpdateTransactionService(UserRepository repository, Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Transactional
    public UserResponseDTO emailUpdater(long id, String email){
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        entity.setEmail(email);
        //Temporary Exception for testing
        throw new RuntimeException("Testing RollBack");
        //return mapper.entityToResponseDTO(entity);
    }

    @Transactional
    public UserResponseDTO namelUpdater(long id, String name){
        UserEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        entity.setName(name);
        //Temporary Exception for testing
        throw new RuntimeException("Testing RollBack");
        //return mapper.entityToResponseDTO(entity);
    }

}
