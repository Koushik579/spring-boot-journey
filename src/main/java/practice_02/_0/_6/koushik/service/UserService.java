package practice_02._0._6.koushik.service;

import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.entity.UserEntity;
import org.springframework.stereotype.Service;
import practice_02._0._6.koushik.exception.ResourceNotFoundException;
import practice_02._0._6.koushik.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    public UserService(UserRepository repository) {
        this.repository = repository;
    }



    public List<UserResponseDTO> allUsers(){
        List<UserEntity> userList= repository.findAll();
        List<UserResponseDTO> users = new ArrayList<>();
        for(UserEntity user : userList){
            UserResponseDTO responseDTO = new UserResponseDTO();
            responseDTO.setName(user.getName());
            responseDTO.setId(user.getId());
            responseDTO.setEmail(user.getEmail());
            users.add(responseDTO);
        }
        return users;
    }

    public UserResponseDTO saveUser(UserEntity entity){
        UserEntity user = repository.save(entity);
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());

        return responseDTO;
    }

    public UserResponseDTO searchUserById(Long id){
        UserEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setName(entity.getName());
        responseDTO.setId(entity.getId());
        responseDTO.setEmail(entity.getEmail());
        return responseDTO;
    }

    public String updateUser(long id, String name){
        UserEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        entity.setName(name);
        repository.save(entity);
        return "User's name successfully updated";
    }
    public String updateEmail(long id, String email){
        UserEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        entity.setEmail(email);
        repository.save(entity);
        return "User's email successfully updated";
    }

    public void deleteUser(long id){
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        repository.deleteById(id);
    }

}
