package be.technobel.materialloc.service.impl;

import be.technobel.materialloc.models.dto.AuthDTO;
import be.technobel.materialloc.models.entity.users.PendingStudent;
import be.technobel.materialloc.models.entity.users.Person;
import be.technobel.materialloc.models.form.LoginForm;
import be.technobel.materialloc.models.form.RegisterForm;
import be.technobel.materialloc.repository.PersonRepository;
import be.technobel.materialloc.service.AuthService;
import be.technobel.materialloc.utils.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authManager, JwtProvider jwtProvider, PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtProvider = jwtProvider;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthDTO login(LoginForm form) {
        authManager.authenticate( new UsernamePasswordAuthenticationToken(form.getLogin(),form.getPassword()) );

        Person person = personRepository.findByLogin(form.getLogin() )
                .orElseThrow();

        String token = jwtProvider.generateToken(person.getLogin(), person.getRole() );

        return AuthDTO.builder()
                .token(token)
                .username(person.getLogin())
                .role(person.getRole())
                .build();
    }

    @Override
    public AuthDTO register(RegisterForm form) {
        PendingStudent pending = form.toPending();
        pending.setPassword( passwordEncoder.encode(form.getPassword()) );
        pending = personRepository.save( pending );

        String token = jwtProvider.generateToken(pending.getLogin(), pending.getRole());

        return AuthDTO.builder()
                .token(token)
                .username(pending.getLogin())
                .role(pending.getRole())
                .build();
    }
}
