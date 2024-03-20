package ch.black.gravel.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.black.gravel.daos.SecretIdentityDAO;
import ch.black.gravel.daos.ArticleDAO;
import ch.black.gravel.daos.PetDAO;
import ch.black.gravel.dtos.PersonDTO;
import ch.black.gravel.dtos.SecretIdentityDTO;
import ch.black.gravel.dtos.PetsDTO;
import ch.black.gravel.entities.Article;
import ch.black.gravel.entities.Person;
import ch.black.gravel.entities.SecretIdentity;
import ch.black.gravel.entities.Pet;
import ch.black.gravel.services.PersonService;
import ch.black.util.exceptions.NotFoundException;

@Controller
@RequestMapping("/people")
public class PeopleWebController {
    private PersonService personService;
    private SecretIdentityDAO secretIdentityDAO;
    private PetDAO petDAO;
    private ArticleDAO articleDAO;

    public PeopleWebController(
        PersonService injectedPersonService, 
        SecretIdentityDAO injectedSecretIdentityDAO,
        PetDAO injectedPetDAO,
        ArticleDAO injectedArticleDAO
    ) {
        personService = injectedPersonService;
        secretIdentityDAO = injectedSecretIdentityDAO;
        petDAO = injectedPetDAO;
        articleDAO = injectedArticleDAO;
    }

    @GetMapping("/find")
    public String getSearchForm(
        @RequestParam(value = "search", required = false) String search, 
        @RequestParam(value = "action", required = false) String action,
        Model model
    ) {
        String localSearch = "";
        model.addAttribute("searchResults", List.of());

        if (search != null && !search.isEmpty() && action != null && !action.isEmpty()) {
            try {
                String[] tokens;
                String firstName, lastName;
                switch (action) {
                    case "secretId":
                        ArrayList<Person> personResults = new ArrayList<>();
                        localSearch = search;
                        List<SecretIdentity> identityResults = secretIdentityDAO.findBySecretName(search);
                        for (SecretIdentity identity : identityResults) {
                            if (identity.getPerson() != null) {
                                personResults.add(identity.getPerson());
                            }
                        }
                        model.addAttribute("searchResults", personResults);
                        break;
                    case "pets":
                        localSearch = search;
                        tokens = search.split("\\s+");
    
                        firstName = (tokens.length > 0) ? tokens[0] : "";
                        lastName = (tokens.length > 1) ? tokens[1] : "";
                        List<Pet> petResults = petDAO.findPetsByOwner(firstName, lastName);
                        model.addAttribute("searchResults", petResults);
                        break;
                    case "articles":
                        localSearch = search;
    
                        tokens = search.split("\\s+");
    
                        firstName = (tokens.length > 0) ? tokens[0] : "";
                        lastName = (tokens.length > 1) ? tokens[1] : "";
                        Person author = personService.findByFullName(firstName, lastName);
                        if (author != null) {
                            List<Article> articleResults = articleDAO.findByAuthor(author);
                            model.addAttribute("searchResults", articleResults);
                        }
                        break;
                    default:
                        break;
                }
            } catch (EmptyResultDataAccessException nfexc) {
                System.err.println("search: " + search + " throws: " + nfexc.getMessage());
                model.addAttribute("searchResults", Arrays.asList("not found"));
            }
        }

        model.addAttribute("search", localSearch);

        return "people/find";
    }

    @GetMapping("/list")
    public String getPeople(Model model) {
        List<Person> people = personService.findAll();
        model.addAttribute("people", people);
        return "people/list";
    }

    @GetMapping("/form")
    public String upsertPerson(
        @RequestParam(value = "personId", required = false) Long personId, 
        Model model
    ) {
        PersonDTO personDTO;
        PetsDTO petsDTO = new PetsDTO();
        SecretIdentityDTO identityDTO = new SecretIdentityDTO();
        if (personId != null) {
            Person person = personService.findById(personId);
            personDTO = new PersonDTO(person);
            if (person.getPets() != null) {
                petsDTO = new PetsDTO(person.getPets());
            }
            if (person.getSecretIdentity() != null) {
                identityDTO = new SecretIdentityDTO(person.getSecretIdentity());
            }
        } else {
            personDTO = new PersonDTO();
        }
        model.addAttribute("person", personDTO);
        model.addAttribute("petsDTO", petsDTO);
        model.addAttribute("secretIdentity", identityDTO);
        return "people/form";
    }

    @PostMapping("/addTitle")
    public String addTitle(
        Model model,
        @ModelAttribute("person") PersonDTO personDTO,
        @ModelAttribute("petsDTO") PetsDTO petsDTO,
        @ModelAttribute("secretIdentity") SecretIdentityDTO identityDTO
    ) {
        petsDTO.addTitle(new Pet("", ""));

        model.addAttribute("person", personDTO);
        model.addAttribute("petsDTO", petsDTO);
        model.addAttribute("secretIdentity", identityDTO);
        return "people/form";
    }

    @PostMapping("/deleteTitle")
    public String deleteTitle(
        Model model,
        @RequestParam(value = "index", required = false) Integer index,
        @ModelAttribute("person") PersonDTO personDTO,
        @ModelAttribute("petsDTO") PetsDTO petsDTO,
        @ModelAttribute("secretIdentity") SecretIdentityDTO identityDTO
    ) {
        petsDTO.removeTitle(index);

        model.addAttribute("person", personDTO);
        model.addAttribute("petsDTO", petsDTO);
        model.addAttribute("secretIdentity", identityDTO);
        return "people/form";
    }

    @PostMapping("/save")
    public String savePerson(
        @ModelAttribute("person") PersonDTO personDTO,
        @ModelAttribute("petsDTO") PetsDTO petsDTO,
        @ModelAttribute("secretIdentity") SecretIdentityDTO identityDTO
    ) {
        Person person;
        SecretIdentity identity;
        List<Pet> removed = new ArrayList<>();

        if (personDTO.getId() == 0) {
            person = new Person(personDTO);
            Timestamp now = new Timestamp(new Date().getTime());
            person.setCreated(now);

            if (!identityDTO.getSecretName().isEmpty()) {
                identity = new SecretIdentity(identityDTO);
                person.setSecretIdentity(identity);
            }

            removed = person.updatePets(petsDTO.getPets());

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

            removed = person.updatePets(petsDTO.getPets());

            personService.update(person);
        }

        for (Pet title : removed) {
            petDAO.delete(title.getId());
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
