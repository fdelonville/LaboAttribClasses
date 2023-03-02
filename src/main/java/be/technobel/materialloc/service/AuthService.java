package be.technobel.materialloc.service;

import be.technobel.materialloc.models.dto.AuthDTO;
import be.technobel.materialloc.models.form.LoginForm;
import be.technobel.materialloc.models.form.RegisterForm;

public interface AuthService {
    AuthDTO login(LoginForm form);
    AuthDTO register(RegisterForm form);
}
