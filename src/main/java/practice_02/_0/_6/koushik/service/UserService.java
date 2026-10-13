package practice_02._0._6.koushik.service;

import jakarta.transaction.Transactional;
import practice_02._0._6.koushik.dto.UpdateNameRequest;
import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.entity.UserEntity;
import org.springframework.stereotype.Service;
import practice_02._0._6.koushik.exception.ResourceNotFoundException;
import practice_02._0._6.koushik.mapper.UserMapper;
import practice_02._0._6.koushik.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
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
        UserEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        entity.setName(request.getName());
        //UserEntity savedEntity = repository.save(entity);
        return mapper.entityToResponseDTO(entity);
    }

    @Transactional
    public UserResponseDTO updateEmail(long id, String email){
        UserEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        entity.setEmail(email);
        //UserEntity savedEntity = repository.save(entity);
        return mapper.entityToResponseDTO(entity);
    }

    public void deleteUser(long id){
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        repository.deleteById(id);
    }

}
