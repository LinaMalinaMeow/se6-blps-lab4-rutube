package black.orange.rutube.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserDto {
    @Email(message = "Невалидный mail")
    @NotEmpty(message = "Mail не может быть пустым!")
    private String email;

    @NotEmpty(message = "Password не должен быть пустым!")
    private String password;
}
