package ch.black.gravel.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.black.gravel.entities.Company;
import ch.black.gravel.services.CompanyService;

@Controller
@RequestMapping("/companies")
public class CompanyWebController {
    private CompanyService service;

    public CompanyWebController(CompanyService injectedService) {
        service = injectedService;
    }

    @GetMapping("/list")
    public String getPeople(Model model) {
        List<Company> entries = service.findAll();
        model.addAttribute("entries", entries);
        return "companies/list";
    }

    @GetMapping("/form")
    public String upsertPerson(@RequestParam(value = "companyId", required = false) Long companyId, Model model) {
        Company entry = null;
        if (companyId != null) {
            entry = service.findById(companyId);
        } else {
            entry = new Company();
        }
        model.addAttribute("entry", entry);
        return "companies/form";
    }

    @PostMapping("/save")
    public String savePerson(@ModelAttribute("entry") Company entry) {
        if (entry != null){
            service.save(entry);
        }
        return "redirect:/companies/list";
    }

    @GetMapping("/delete")
    public String deletePerson(@RequestParam(value = "companyId", required = false) Long companyId, Model model) {
        if (companyId != null) {
            service.delete(companyId);
        }
        return "redirect:/companies/list";
    }

}
