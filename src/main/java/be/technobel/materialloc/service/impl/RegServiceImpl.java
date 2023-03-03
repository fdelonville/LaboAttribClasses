package be.technobel.materialloc.service.impl;

import be.technobel.materialloc.exceptions.NotFoundException;
import be.technobel.materialloc.models.dto.PersonDTO;
import be.technobel.materialloc.models.entity.users.*;
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
        Person person = personRepository.findById(id).orElseThrow();
        Student student = new Student();

        student.setPassword(person.getPassword());
        student.setFirstName(person.getFirstName());
        student.setLogin(person.getLogin());
        student.setLastName(person.getLastName());
        student.setEmail(person.getEmail());
        student.setAddress(person.getAddress());
        student.setRole("STUDENT");
        personRepository.delete(person);
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
