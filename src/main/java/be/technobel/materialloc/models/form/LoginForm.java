package be.technobel.materialloc.models.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
