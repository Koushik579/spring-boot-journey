package practice_02._0._6.koushik.repository;

import practice_02._0._6.koushik.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReposiretory extends JpaRepository<UserEntity, Long> {
}
