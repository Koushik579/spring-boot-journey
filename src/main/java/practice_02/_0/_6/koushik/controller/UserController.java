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
    public ResponseEntity<List<UserResponseDTO>> getallUsers(){

        return ResponseEntity.ok(service.allUsers());
    }

    @PostMapping("/saveuser")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserEntity entity){

        return ResponseEntity.status(201).body(service.saveUser(entity));
    }

    @PostMapping("/updatename/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable long id,@Valid @RequestParam String name){
        return ResponseEntity.status(200).body(service.updateUser(id, name));
    }

    @PostMapping("/updateemail/{id}")
    public ResponseEntity<String> updateEmailById(@PathVariable long id,@Valid @RequestParam String email){
        return ResponseEntity.ok(service.updateEmail(id, email));
    }

    @GetMapping("/searchUser/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable long id){

        return ResponseEntity.ok(service.searchUserById(id));
    }

    @DeleteMapping("delUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id){

        return ResponseEntity.status(200).body(service.deleteUser(id));
    }

}
