package com.eren.esmahan.peopledbweb.controller;

import com.eren.esmahan.peopledbweb.model.Person;
import com.eren.esmahan.peopledbweb.repository.FileStorageRepository;
import com.eren.esmahan.peopledbweb.repository.PersonRepository;
import com.eren.esmahan.peopledbweb.service.PersonService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.StringHelper.format;


@Controller
@RequestMapping("/people")
@Log4j2
public class PeopleController {
    public static final String DISPO = """
             attachment; filename = "%s"
            """;
    private PersonRepository personRepository;
    private FileStorageRepository fileStorageRepository;
    private PersonService personService;


    public PeopleController(PersonRepository personRepository ,
                            FileStorageRepository fileStorageRepository,
                            PersonService personService)
    {
        this.personRepository = personRepository;
        this.fileStorageRepository=fileStorageRepository;
        this.personService=personService;
    }

    @ModelAttribute("people") //think like a map,"people"is key,people is a value ,list of people
    public Page<Person> getPeople(@PageableDefault(size = 10) Pageable page)
    {
        return personService.findAll(page);
    }

    @ModelAttribute
    public Person getPerson()// key "person" by default , if we don't specify get class name in lower case
    {
        return new Person();
    }

    @GetMapping
    public String showPeoplePage()
    {
        return "people";
    }

    @GetMapping("/images/{resource}")
    public ResponseEntity<Resource> getResource(@PathVariable String resource)
    {
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, format(DISPO, resource))
                .body(fileStorageRepository.findByName(resource));
    }


    @PostMapping
    public String savePeople(@Valid Person person, Errors errors, @RequestParam("photoFilename") MultipartFile photoFile) throws IOException {
       log.info(person);
       log.info("Filename " + photoFile.getOriginalFilename());
       log.info("File size :" + photoFile.getSize());
       log.info("Errors" + errors);
        if (!errors.hasErrors())
        {
            personService.save(person , photoFile.getInputStream());
            return "redirect:people";
        }
        return "people";
    }

    @PostMapping(params = "action=delete")
    public String deletePeople(@RequestParam Optional<List<Long>> selection)
    {
       log.info(selection);
        if (selection.isPresent())
        {
          //  personRepository.deleteAllById(selection.get());
            personService.deleteAllById(selection.get());
        }
        return "redirect:people";
    }


    @PostMapping(params = "action=edit")
    public String editPerson(@RequestParam Optional<List<Long>> selection ,Model model)
    {
       log.info(selection);
        if (selection.isPresent())
        {
           Optional<Person> person= personRepository.findById(selection.get().get(0 ));
           model.addAttribute("person",person);
        }
        return "people";
    }

    @PostMapping(params = "action=import")
    public String importCSV(@RequestParam MultipartFile csvFile){
        log.info("File name :"+ csvFile.getOriginalFilename());
        log.info("File size :"+ csvFile.getSize());
        try {
            personService.importCSV(csvFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:people";
    }

}
