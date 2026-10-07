package practice_02._0._6.koushik.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.entity.UserEntity;
import practice_02._0._6.koushik.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/allUsers")
    public List<UserResponseDTO> getallUsers(){
        return service.allUsers();
    }

    @PostMapping("/saveuser")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserEntity entity){

        return ResponseEntity.ok(service.saveUser(entity));
    }

    @PostMapping("/updatename/{id}")
    public String updateUserById(@PathVariable long id,@Valid @RequestParam String name){
        return service.updateUser(id, name);
    }

    @PostMapping("/updateemail/{id}")
    public String updateEmailById(@PathVariable long id,@Valid @RequestParam String email){
        return service.updateEmail(id, email);
    }

    @GetMapping("/searchUser/{id}")
    public UserResponseDTO findUserById(@PathVariable long id){
        return service.searchUserById(id);
    }

    @DeleteMapping("delUser/{id}")
    public String deleteUser(@PathVariable long id){
        return service.deleteUser(id);
    }

}
