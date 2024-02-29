package io.black.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import io.black.rest.entities.Company;
import io.black.rest.entities.Contract;
import io.black.rest.entities.Person;
import io.black.rest.repositories.ContractRepository;
import io.black.rest.services.CompanyService;
import io.black.rest.services.PersonService;

@SpringBootApplication(
	scanBasePackages = {
		"io.black.rest",
		"io.black.util"
	}
)
public class RestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PersonService personService, CompanyService companyService, ContractRepository contractRepository){
		return runner -> {
			this.createTestSets(personService, companyService, contractRepository);
		};
	}

	private void createTestSets(PersonService personService, CompanyService companyService, ContractRepository contractRepository){
		List<Person> people = personService.findAll();
		if (people.isEmpty()){
			people = getPeople();
			for (Person person : people) {
				personService.save(person);
				System.out.println("[INFO]: Person created " + person);
			}
		}
		List<Company> companies = companyService.findAll();
		if (companies.isEmpty()){
			companies = getCompanies();
			for (Company company : companies) {
				companyService.save(company);
				System.out.println("[INFO]: Company created " + company);
			}
		}
		List<Contract> contracts = contractRepository.findAll();
		if (contracts.isEmpty()){
			contracts = createContracts();
			for (Contract contract : contracts) {
				contractRepository.save(contract);
				System.out.println("[INFO]: Contract created " + contract);
			}
		}
	}

	private List<Person> getPeople(){
        List<Person> entries = new ArrayList<>();
        try {
            List<LinkedHashMap<String, Object>> data = readTestData("people");
            for (LinkedHashMap<String, Object> jsonEntry : data) {
                Person localEntry = new Person(
					(String) jsonEntry.get("firstName"),
					(String) jsonEntry.get("lastName"),
					(String) jsonEntry.get("email")
				);
                entries.add(localEntry);
            }
        } catch (Exception e) {
            System.out.println( "getPeople() ERROR: " + e.getMessage());
        }
        return entries;
	}

	private List<Company> getCompanies(){
        List<Company> entries = new ArrayList<>();
        try {
            List<LinkedHashMap<String, Object>> data = readTestData("companies");
            for (LinkedHashMap<String, Object> jsonEntry : data) {
                Company localEntry = new Company(
					(String) jsonEntry.get("companyName"),
					(String) jsonEntry.get("contactEmail")
				);
                entries.add(localEntry);
            }
        } catch (Exception e) {
            System.out.println( "getPeople() ERROR: " + e.getMessage());
        }
        return entries;
	}

	private List<Contract> createContracts(){
        List<Contract> entries = new ArrayList<>();
        try {
            List<LinkedHashMap<String, Object>> data = readTestData("contracts");
            for (LinkedHashMap<String, Object> jsonEntry : data) {
                Contract localEntry = new Contract(
					((BigInteger) jsonEntry.get("contractWorkload")).intValue(),
					((BigInteger) jsonEntry.get("salary")).intValue(),
					((BigInteger) jsonEntry.get("companyId")).intValue(),
					((BigInteger) jsonEntry.get("personId")).intValue()
				);
                entries.add(localEntry);
            }
        } catch (Exception e) {
            System.out.println( "getPeople() ERROR: " + e.getMessage());
        }
        return entries;
	}

	private List<LinkedHashMap<String, Object>> readTestData(String set){
		List<LinkedHashMap<String, Object>> map = null;
        try {
            File file = ResourceUtils.getFile("classpath:files/" + set + ".json");
            InputStream inputStream = new FileInputStream(file);
            JSONParser parser = new JSONParser(inputStream);
            LinkedHashMap<String, Object> json = parser.object();
            map = (List<LinkedHashMap<String, Object>>) json.get("data");
        } catch (Exception e) {
            System.out.println( "readTestData() ERROR: " + e.getMessage());
        }
		return map;
	}

}
