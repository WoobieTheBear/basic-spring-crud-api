package ch.black.gravel.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.black.gravel.daos.SecretIdentityDAO;
import ch.black.gravel.dtos.PersonDTO;
import ch.black.gravel.dtos.SecretIdentityDTO;
import ch.black.gravel.entities.Person;
import ch.black.gravel.entities.SecretIdentity;
import ch.black.gravel.services.PersonService;

@Controller
@RequestMapping("/people")
public class PeopleWebController {
    private PersonService personService;
    private SecretIdentityDAO secretIdentityDAO;

    public PeopleWebController(PersonService injectedPersonService, SecretIdentityDAO injectedsecretIdentityDAO) {
        personService = injectedPersonService;
        secretIdentityDAO = injectedsecretIdentityDAO;
    }

    @GetMapping("/find")
    public String getSearchForm(@RequestParam(value = "search", required = false) String search, Model model) {
        ArrayList<Person> searchResults = new ArrayList<>();
        String localSearch = "";

        if (search != null && !search.isEmpty()) {
            localSearch = search;
            List<SecretIdentity> identityResults = secretIdentityDAO.findBySecretName(search);
            for (SecretIdentity identity : identityResults) {
                if (identity.getPerson() != null) {
                    searchResults.add(identity.getPerson());
                }
            }
        }

        model.addAttribute("search", localSearch);
        model.addAttribute("searchResults", searchResults);

        return "people/find";
    }

    @GetMapping("/list")
    public String getPeople(Model model) {
        List<Person> people = personService.findAll();
        model.addAttribute("people", people);
        return "people/list";
    }

    @GetMapping("/form")
    public String upsertPerson(@RequestParam(value = "personId", required = false) Long personId, Model model) {
        PersonDTO personDTO;
        SecretIdentityDTO identityDTO = new SecretIdentityDTO();
        if (personId != null) {
            Person person = personService.findById(personId);
            personDTO = new PersonDTO(person);
            if (person.getSecretIdentity() != null) {
                identityDTO = new SecretIdentityDTO(person.getSecretIdentity());
            }
        } else {
            personDTO = new PersonDTO();
        }
        model.addAttribute("person", personDTO);
        model.addAttribute("secretIdentity", identityDTO);
        return "people/form";
    }

    @PostMapping("/save")
    public String savePerson(
        @ModelAttribute("person") PersonDTO personDTO,
        @ModelAttribute("secretIdentity") SecretIdentityDTO identityDTO
    ) {
        Person person;
        SecretIdentity identity;

        if (personDTO.getId() == 0) {
            person = new Person(personDTO);
            Timestamp now = new Timestamp(new Date().getTime());
            person.setCreated(now);

            if (!identityDTO.getSecretName().isEmpty()) {
                identity = new SecretIdentity(identityDTO);
                person.setSecretIdentity(identity);
            }

            personService.save(person);
        } else {
            person = personService.findById(personDTO.getId());
            person.mergeDTO(personDTO);
            identity = person.getSecretIdentity();

            if (identity != null && !identityDTO.getSecretName().isEmpty()) {
                identity = person.getSecretIdentity();
                identity.mergeDTO(identityDTO);

                person.setSecretIdentity(identity);

            } else if (identity == null && !identityDTO.getSecretName().isEmpty()) {
                identity = new SecretIdentity(identityDTO);
                person.setSecretIdentity(identity);

            } else if (identityDTO.getSecretName().isEmpty()) {
                identity = person.getSecretIdentity();
                if (identity != null) {
                    secretIdentityDAO.delete(identity.getId());
                }
            }

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
