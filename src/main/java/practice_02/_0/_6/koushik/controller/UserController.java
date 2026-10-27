package practice_02._0._6.koushik.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import practice_02._0._6.koushik.dto.AuditResponseDTO;
import practice_02._0._6.koushik.dto.NameUpdateRequest;
import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.entity.UserEntity;
import practice_02._0._6.koushik.service.AuditService;
import practice_02._0._6.koushik.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final AuditService auditService;
    public UserController(UserService userService, AuditService auditService) {
        this.userService = userService;
        this.auditService = auditService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getallUsers(){

        return ResponseEntity.ok(userService.allUsers());
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserEntity entity){

        UserResponseDTO savedUser = userService.saveUser(entity);

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
    public ResponseEntity<UserResponseDTO> updateUserById(
            @PathVariable long id,
            @Valid @RequestBody NameUpdateRequest request){
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @PatchMapping("user/email/{id}")
    public ResponseEntity<UserResponseDTO> updateEmailById(
            @PathVariable long id,
            @Valid @RequestParam String email){
        return ResponseEntity.ok(userService.updateEmail(id, email));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable long id){

        return ResponseEntity.ok(userService.searchUserById(id));
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    //Audit logs

    @GetMapping("log/user/{id}")
    public ResponseEntity<List<AuditResponseDTO>> userUpdateLogs(@PathVariable long id){
        return ResponseEntity.ok(auditService.getLogsByUser(id));
    }

}
