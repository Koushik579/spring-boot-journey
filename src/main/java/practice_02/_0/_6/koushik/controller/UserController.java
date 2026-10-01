package practice_02._0._6.koushik.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import practice_02._0._6.koushik.entity.UserEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import practice_02._0._6.koushik.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/saveuser")
    public String addUser(@RequestBody UserEntity entity){
        return service.saveUser(entity);
    }

}
