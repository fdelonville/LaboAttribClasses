package be.technobel.materialloc.models.form;

import be.technobel.materialloc.models.entity.users.*;
import lombok.Data;

@Data
public class RegisterForm {
    private String login;

    private String password;

    private String email;
    
    private String firstName;
    
    private String lastName;

    public PendingStudent toPending(){
        PendingStudent pendingStudent = new PendingStudent();

        pendingStudent.setLogin(login);
        pendingStudent.setPassword(password);
        pendingStudent.setEmail(email);
        pendingStudent.setRole("PENDING");
        pendingStudent.setEnabled(true);
        pendingStudent.setFirstName(firstName);
        pendingStudent.setLastName(lastName);

        return pendingStudent;
    }

    public Teacher toTeacher(){
        Teacher teacher = new Teacher();

        teacher.setLogin(login);
        teacher.setPassword(password);
        teacher.setEmail(email);
        teacher.setEnabled(true);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);

        return teacher;
    }

    public Admin toAdmin(){
        Admin admin = new Admin();

        admin.setLogin(login);
        admin.setPassword(password);
        admin.setEmail(email);
        admin.setEnabled(true);
        admin.setFirstName(firstName);
        admin.setLastName(lastName);

        return admin;
    }
}
