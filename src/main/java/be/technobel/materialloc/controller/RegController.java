package be.technobel.materialloc.controller;

import be.technobel.materialloc.models.dto.PersonDTO;
import be.technobel.materialloc.service.RegService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegController {
    private final RegService regService;

    public RegController(RegService regService) {
        this.regService = regService;
    }

    @GetMapping("/registration/all")
    public List<PersonDTO> getAll(){
        return regService.getAll();
    }

    @GetMapping("/registration/{id:[0-9]+}")
    public PersonDTO getOne(@PathVariable long id){
        return regService.getOne(id);
    }

    @PatchMapping("/registration/{id:[0-9]+}/approve")
    public void approve(@PathVariable Long id){
        regService.approve(id);
    }

    @PatchMapping("/registration/{id:[0-9]+}/refuse")
    public void refuse(@PathVariable Long id){
        regService.refuse(id);
    }


}
