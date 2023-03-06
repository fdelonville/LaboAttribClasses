package be.technobel.materialloc.utils;

import be.technobel.materialloc.models.entity.Material;
import be.technobel.materialloc.models.entity.Request;
import be.technobel.materialloc.models.entity.RequestStatus;
import be.technobel.materialloc.models.entity.users.Admin;
import be.technobel.materialloc.models.entity.users.Student;
import be.technobel.materialloc.models.entity.users.Teacher;
import be.technobel.materialloc.repository.MaterialRepository;
import be.technobel.materialloc.repository.PersonRepository;
import be.technobel.materialloc.repository.RequestRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
//@Log4j2
public class DataInit implements InitializingBean {

    private final PasswordEncoder passwordEncoder;

    private final PersonRepository personRepository;

    private final RequestRepository requestRepository;

    private final MaterialRepository materialRepository;

    public DataInit(PasswordEncoder passwordEncoder, PersonRepository personRepository, RequestRepository requestRepository, MaterialRepository materialRepository) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
        this.requestRepository = requestRepository;
        this.materialRepository = materialRepository;
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
        student.setFirstName("Aaaa");
        student.setLastName("Aaaa");
        student.setEnabled(true);

        Teacher teacher = new Teacher();
        teacher.setLogin("teacher");
        teacher.setPassword(passwordEncoder.encode("pass"));
        teacher.setEnabled(true);

        personRepository.save(admin);
        personRepository.save(student);
        personRepository.save(teacher);

        Request request = new Request();
        request.setStatus(RequestStatus.PENDING);
        request.setDate(LocalDate.now().plusDays(10));
        request.setBeginTime(LocalTime.now());
        request.setEndTime(LocalTime.now().plusHours(2));
        request.setMadeBy(student);
        Long id = 1L;
        Material material = materialRepository.findById(id).orElseThrow();
        request.getMaterials().add(material);
        request.setNeededCapacity(20);
        request.setJustification("none");
        request.setAdditionalNotes("none");
        requestRepository.save(request);
    }
}
