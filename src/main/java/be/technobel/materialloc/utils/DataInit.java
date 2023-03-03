package be.technobel.materialloc.utils;

import be.technobel.materialloc.models.entity.Address;
import be.technobel.materialloc.models.entity.users.Admin;
import be.technobel.materialloc.models.entity.users.Student;
import be.technobel.materialloc.models.entity.users.Teacher;
import be.technobel.materialloc.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
//@Log4j2
public class DataInit implements InitializingBean {

    private final PasswordEncoder passwordEncoder;

    private final PersonRepository personRepository;

    public DataInit(PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Admin admin = new Admin();
        admin.setLogin("admin");
        admin.setPassword(passwordEncoder.encode("pass"));
        admin.setEnabled(true);


        Student student = new Student();
        student.setLogin("student");
        student.setPassword(passwordEncoder.encode("pass"));
        student.setEnabled(true);

        Teacher teacher = new Teacher();
        teacher.setLogin("teacher");
        teacher.setPassword(passwordEncoder.encode("pass"));
        teacher.setEnabled(true);

        personRepository.save(admin);
        personRepository.save(student);
        personRepository.save(teacher);
    }
}
