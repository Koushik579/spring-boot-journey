package practice_02._0._6.koushik.service;

import org.springframework.http.ResponseEntity;
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
            users.add(responseDTO);
        }
        return users;
    }

    public String saveUser(UserEntity entity){
        repository.save(entity);
        return "user saved\n"+entity.getName()+"\n****Enjoy****";
    }

    public UserResponseDTO searchUserById(Long id){
        UserEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setName(entity.getName());
        responseDTO.setId(entity.getId());
        return responseDTO;
    }

    public String updateUser(long id, String name){
        UserEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        String oldName = entity.getName();
        entity.setName(name);
        repository.save(entity);
        return "User's name updated from to "+ name;
    }
    public String updateEmail(long id, String email){
        UserEntity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        String oldName = entity.getEmail();
        entity.setEmail(email);
        repository.save(entity);
        return "User's email updated to "+ email;
    }

    public String deleteUser(long id){
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        repository.deleteById(id);
        return "User deleted";
    }

}
