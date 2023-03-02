package be.technobel.materialloc.service;

import be.technobel.materialloc.models.dto.PersonDTO;
import be.technobel.materialloc.models.form.RegisterForm;

import java.util.List;

public interface RegService {

    void approve(Long id);

    void refuse(Long id);

    List<PersonDTO> getAll();

    PersonDTO getOne(Long id);

    void insertTeacher(RegisterForm form);

    void insertAdmin(RegisterForm form);

}
