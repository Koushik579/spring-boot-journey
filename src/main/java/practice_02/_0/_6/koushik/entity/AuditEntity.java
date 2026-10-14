package practice_02._0._6.koushik.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "audit_tb")
public class AuditEntity {
    @NotNull(message = "Enter a valid ID")
    private long userID;

    @NotBlank(message = "Name field must not be empty")
    private String name;

    @NotBlank(message = "Email field must not be empty")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Enter a valid reason")
    private String comment;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
