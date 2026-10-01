package practice_02._0._6.koushik.service;

import practice_02._0._6.koushik.entity.UserEntity;
import org.springframework.stereotype.Service;
import practice_02._0._6.koushik.repository.UserReposiretory;

@Service
public class UserService {
    private final UserReposiretory repository;
    public UserService(UserReposiretory repository) {
        this.repository = repository;
    }

    public String saveUser(UserEntity entity){
        repository.save(entity);
        return "user saved\n"+entity.getName()+"\n****Enjoy****";
    }
}
