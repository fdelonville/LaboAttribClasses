package be.technobel.materialloc.models.form;

import be.technobel.materialloc.models.entity.users.Person;
import be.technobel.materialloc.models.entity.users.Student;
import lombok.Data;

@Data
public class RegisterForm {
    private String login;

    private String password;

    private String email;

    public Person toEntity(){
        Person person = new Person();

        person.setLogin(login);
        person.setPassword(password);
        person.setEmail(email);

        return person;
    }
}
