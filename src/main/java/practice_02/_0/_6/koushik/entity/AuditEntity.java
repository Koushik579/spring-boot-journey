package practice_02._0._6.koushik.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import practice_02._0._6.koushik.enums.AuditStatus;

@Entity
@Table(name = "audit_tb")
public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "enter a valid user ID")
    private long userID;

    @NotBlank(message = "Name field must not be empty")
    private String name;

    @NotBlank(message = "Email field must not be empty")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Enter a valid reason")
    private String comment;

    @NotBlank(message = "Fill the date and time field")
    private String dateTime;

    @NotNull(message = "What is the status of the request?")
    @Enumerated(EnumType.STRING)
    private AuditStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public AuditStatus getStatus() {
        return status;
    }

    public void setStatus(AuditStatus status) {
        this.status = status;
    }
}
