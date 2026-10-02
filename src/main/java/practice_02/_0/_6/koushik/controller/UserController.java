package practice_02._0._6.koushik.controller;

import org.springframework.web.bind.annotation.*;
import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.entity.UserEntity;
import practice_02._0._6.koushik.service.UserService;

import javax.swing.text.html.parser.Entity;
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
    public String addUser(@RequestBody UserEntity entity){
        return service.saveUser(entity);
    }

    @PostMapping("/update/{id}")
    public String updateUserById(@PathVariable long id, @RequestParam String name){
        return service.updateUser(id, name);
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
