package ch.black.gravel.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.black.gravel.entities.Contract;
import ch.black.gravel.repositories.ContractRepository;

@Controller
@RequestMapping("/workcontracts")
public class ContractWebController {
    private ContractRepository service;

    public ContractWebController(ContractRepository injectedService) {
        service = injectedService;
    }

    @GetMapping("/list")
    public String getPeople(Model model) {
        List<Contract> entries = service.findAll();
        model.addAttribute("entries", entries);
        return "contracts/list";
    }

    @GetMapping("/form")
    public String upsertPerson(@RequestParam(value = "contractId", required = false) Long contractId, Model model) {
        Contract entry = null;
        if (contractId != null) {
            Optional<Contract> result = service.findById(contractId);
            if (result != null) {
                entry = result.get();
            }
        }
        if (entry == null) {
            entry = new Contract();
        }
        model.addAttribute("entry", entry);
        return "contracts/form";
    }

    @PostMapping("/save")
    public String savePerson(@ModelAttribute("entry") Contract entry) {
        if (entry != null){
            service.save(entry);
        }
        return "redirect:/workcontracts/list";
    }

    @GetMapping("/delete")
    public String deletePerson(@RequestParam(value = "contractId", required = false) Long contractId, Model model) {
        if (contractId != null) {
            service.deleteById(contractId);
        }
        return "redirect:/workcontracts/list";
    }

}
