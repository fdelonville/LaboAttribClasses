package be.technobel.materialloc.service.impl;

import be.technobel.materialloc.exceptions.NotFoundException;
import be.technobel.materialloc.models.dto.PersonDTO;
import be.technobel.materialloc.models.entity.users.Admin;
import be.technobel.materialloc.models.entity.users.Person;
import be.technobel.materialloc.models.entity.users.Student;
import be.technobel.materialloc.models.entity.users.Teacher;
import be.technobel.materialloc.models.form.RegisterForm;
import be.technobel.materialloc.repository.PersonRepository;
import be.technobel.materialloc.service.RegService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegServiceImpl implements RegService {

    private final PersonRepository personRepository;

    public RegServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void approve(Long id) {
        Student student = (Student)personRepository.findById(id).orElseThrow();

        student.setRole("STUDENT");
        personRepository.save(student);
    }

    @Override
    public void refuse(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findAll().stream()
                .filter(p -> p.getRole().equals("PENDING"))
                .map(PersonDTO::toDto)
                .toList();
    }

    @Override
    public PersonDTO getOne(Long id) {
        Person person = personRepository.findById(id).orElseThrow();
        return PersonDTO.toDto(person);
    }

    @Override
    public void insertTeacher(RegisterForm form) {
        Teacher teacher = form.toTeacher();
        personRepository.save(teacher);
    }

    @Override
    public void insertAdmin(RegisterForm form) {
        Admin admin = form.toAdmin();
        personRepository.save(admin);
    }
}
