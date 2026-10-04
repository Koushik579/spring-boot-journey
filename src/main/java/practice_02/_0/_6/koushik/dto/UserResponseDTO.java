package practice_02._0._6.koushik.dto;

import jakarta.validation.constraints.NotBlank;

public class UserResponseDTO {

    @NotBlank(message = "Enter a valid name")
    private String name;
    private long id;
    @NotBlank(message = "Enter a Valid Email Id")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
