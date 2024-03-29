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

import ch.black.gravel.entities.Company;
import ch.black.gravel.services.CompanyService;
import ch.black.util.exceptions.NotFoundException;
import ch.black.util.exceptions.PublicResponse;

@RestController
@RequestMapping("/api")
public class CompanyRestController {
    private CompanyService companyService;

    // constructor injection
    public CompanyRestController(CompanyService injectedService) {
        this.companyService = injectedService;
    }
    @GetMapping("/companies")
    public List<Company> sendAllEntries(){
        return companyService.findAll();
    }
    
    @GetMapping("/companies/{entryId}")
    public Company sendEntryById(@PathVariable("entryId") int id) {
        Company found = companyService.findById(id);
        if (found == null) {
            throw new NotFoundException("company.id = '" + id + "' - was not found");
        }
        return found;
    }

    @PostMapping("/companies")
    public Company handleCreate(@RequestBody Company entry) {
        entry.setId(0);
        return companyService.update(entry);
    }

    @PutMapping("/companies")
    public Company handleUpdate(@RequestBody Company entry) {
        return companyService.update(entry);
    }

    @DeleteMapping("/companies/{entryId}")
    public PublicResponse handleDelete(@PathVariable("entryId") int id) {
        Company entry = companyService.findById(id);
        if (entry == null) {
            throw new NotFoundException("company.id = '" + id + "' - was not found");
        }
        companyService.delete(id);
        return new PublicResponse(
            HttpStatus.OK.value(),
            "Company with id " + id + " was deleted.",
            System.currentTimeMillis()
        );
    }

}
