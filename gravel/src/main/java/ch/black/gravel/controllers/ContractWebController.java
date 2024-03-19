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

import ch.black.gravel.dtos.ContractDTO;
import ch.black.gravel.entities.Company;
import ch.black.gravel.entities.Contract;
import ch.black.gravel.entities.Person;
import ch.black.gravel.repositories.ContractRepository;
import ch.black.gravel.services.CompanyService;
import ch.black.gravel.services.PersonService;

/*
 * [NOTE]: there is no ContractRestController since the api
 *         is provided by the ContractRepository
 */

@Controller
@RequestMapping("/workcontracts")
public class ContractWebController {
    private ContractRepository contractService;
    private PersonService personService;
    private CompanyService companyService;

    public ContractWebController(
        ContractRepository injectedService,
        PersonService injectedPersonService,
        CompanyService injectedCompanyService
    ) {
        contractService = injectedService;
        personService = injectedPersonService;
        companyService = injectedCompanyService;
    }

    @GetMapping("/list")
    public String getPeople(Model model) {
        List<Contract> entries = contractService.findAll();
        model.addAttribute("entries", entries);
        return "contracts/list";
    }

    @GetMapping("/form")
    public String upsertPerson(@RequestParam(value = "contractId", required = false) Long contractId, Model model) {
        Contract contract = null;
        ContractDTO contractDTO = null;

        if (contractId != null) {
            Optional<Contract> result = contractService.findById(contractId);
            if (result != null) {
                contract = result.get();
                contractDTO = new ContractDTO(contract);
            }
        }
        if (contract == null) {
            contractDTO = new ContractDTO();
        }
        List<Person> people = personService.findAll();
        List<Company> companies = companyService.findAll();
        contractDTO.setPeople(people);
        contractDTO.setCompanies(companies);
        model.addAttribute("contractDTO", contractDTO);
        return "contracts/form";
    }

    @PostMapping("/save")
    public String savePerson(@ModelAttribute("contractDTO") ContractDTO contractDTO) {
        Contract contract = new Contract(contractDTO);
        Person person = personService.findById(contractDTO.getPersonId());
        Company company = companyService.findById(contractDTO.getCompanyId());
        contract.setCompany(company);
        contract.setPerson(person);

        contractService.save(contract);

        return "redirect:/workcontracts/list";
    }

    @GetMapping("/delete")
    public String deletePerson(@RequestParam(value = "contractId", required = false) Long contractId, Model model) {
        if (contractId != null) {
            contractService.deleteById(contractId);
        }
        return "redirect:/workcontracts/list";
    }

}
