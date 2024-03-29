package ch.black.gravel.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.black.gravel.entities.Person;
import ch.black.gravel.services.PersonService;
import ch.black.util.exceptions.NotFoundException;
import ch.black.util.exceptions.PublicResponse;

@RestController
@RequestMapping("/api")
public class PeopleRestController {
    private PersonService personService;

    // constructor injection
    public PeopleRestController(PersonService injectedPersonService) {
        this.personService = injectedPersonService;
    }
    
    @GetMapping("/people")
    public List<Person> sendAllEntries(){
        return personService.findAll();
    }
    
    @GetMapping("/people/{entryId}")
    public Person sendEntryById(@PathVariable("entryId") int id) {
        Person found = personService.findById(id);
        if (found == null) {
            throw new NotFoundException("person.id = '" + id + "' - was not found");
        }
        return found;
    }

    @PostMapping("/people")
    public Person handleCreate(@RequestBody Person entry) {
        entry.setId(0);
        return personService.update(entry);
    }

    @PutMapping("/people")
    public Person handleUpdate(@RequestBody Person entry) {
        return personService.update(entry);
    }

    @DeleteMapping("/people/{entryId}")
    public PublicResponse handleDelete(@PathVariable("entryId") int id) {
        Person entry = personService.findById(id);
        if (entry == null) {
            throw new NotFoundException("person.id = '" + id + "' - was not found");
        }
        personService.delete(id);
        return new PublicResponse(
            HttpStatus.OK.value(),
            "Person with id " + id + " was deleted.",
            System.currentTimeMillis()
        );
    }

}
