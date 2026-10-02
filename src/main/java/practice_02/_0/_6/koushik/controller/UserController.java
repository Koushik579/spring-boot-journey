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

    @PostMapping("/update")
    public String updateUserById(@RequestParam long id, @RequestBody String name){
        return service.updateUser(id, name);
    }

    @GetMapping("/searchUser")
    public UserResponseDTO findUserById(@RequestParam long id){
        return service.searchUserById(id);
    }

    @DeleteMapping("delUser")
    public String deleteUser(@RequestParam long id){
        return service.deleteUser(id);
    }

}
