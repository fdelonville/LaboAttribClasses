package be.technobel.materialloc.models.form;

import be.technobel.materialloc.models.entity.users.Admin;
import be.technobel.materialloc.models.entity.users.Person;
import be.technobel.materialloc.models.entity.users.Student;
import be.technobel.materialloc.models.entity.users.Teacher;
import lombok.Data;

@Data
public class RegisterForm {
    private String login;

    private String password;

    private String email;

    public Person toPerson(){
        Person person = new Person();

        person.setLogin(login);
        person.setPassword(password);
        person.setEmail(email);
        person.setRole("PENDING");

        return person;
    }

    public Teacher toTeacher(){
        Teacher teacher = new Teacher();

        teacher.setLogin(login);
        teacher.setPassword(password);
        teacher.setEmail(email);

        return teacher;
    }

    public Admin toAdmin(){
        Admin admin = new Admin();

        admin.setLogin(login);
        admin.setPassword(password);
        admin.setEmail(email);

        return admin;
    }
}
