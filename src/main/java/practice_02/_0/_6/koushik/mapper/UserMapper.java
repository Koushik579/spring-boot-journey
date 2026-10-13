package practice_02._0._6.koushik.mapper;

import org.springframework.stereotype.Component;
import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.entity.UserEntity;

@Component
public class UserMapper {
    public UserResponseDTO entityToResponseDTO(UserEntity entity){
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setEmail(entity.getEmail());
        return responseDTO;
    }
}
