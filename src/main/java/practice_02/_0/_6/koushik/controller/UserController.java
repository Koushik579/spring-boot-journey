package practice_02._0._6.koushik.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import practice_02._0._6.koushik.dto.UpdateNameRequest;
import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.entity.UserEntity;
import practice_02._0._6.koushik.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getallUsers(){

        return ResponseEntity.ok(service.allUsers());
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserEntity entity){

        UserResponseDTO savedUser = service.saveUser(entity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/user/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(savedUser);
    }

    @PatchMapping("user/name/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable long id,@Valid @RequestBody UpdateNameRequest request){
        return ResponseEntity.ok(service.updateUser(id, request));
    }

    @PatchMapping("user/email/{id}")
    public ResponseEntity<UserResponseDTO> updateEmailById(@PathVariable long id,@Valid @RequestParam String email){
        return ResponseEntity.ok(service.updateEmail(id, email));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable long id){

        return ResponseEntity.ok(service.searchUserById(id));
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
