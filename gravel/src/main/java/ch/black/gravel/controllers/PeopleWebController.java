package ch.black.gravel.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.black.gravel.entities.Person;
import ch.black.gravel.services.PersonService;

@Controller
@RequestMapping("/people")
public class PeopleWebController {
    private PersonService personService;

    public PeopleWebController(PersonService injectedPersonService) {
        personService = injectedPersonService;
    }

    @GetMapping("/list")
    public String getPeople(Model model) {
        List<Person> people = personService.findAll();
        model.addAttribute("people", people);
        return "people/list";
    }

    @GetMapping("/form")
    public String upsertPerson(@RequestParam(value = "personId", required = false) Long personId, Model model) {
        Person person;
        if (personId != null) {
            person = personService.findById(personId);
        } else {
            person = new Person();
        }
        model.addAttribute("person", person);
        return "people/form";
    }

    @PostMapping("/save")
    public String savePerson(@ModelAttribute("person") Person person) {

        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        person.setCreationstamp(now);
        person.setLastlogin(now);

        if (person.getId() == 0) {
            personService.save(person);
        } else {
            personService.update(person);
        }


        return "redirect:/people/list";
    }

    @GetMapping("/delete")
    public String deletePerson(@RequestParam(value = "personId", required = false) Long personId, Model model) {
        if (personId != null) {
            personService.delete(personId);
        }
        return "redirect:/people/list";
    }

}
