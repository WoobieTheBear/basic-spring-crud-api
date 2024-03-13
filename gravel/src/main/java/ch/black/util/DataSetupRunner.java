package ch.black.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.util.ResourceUtils;

import ch.black.gravel.entities.Company;
import ch.black.gravel.entities.Contract;
import ch.black.gravel.entities.Person;
import ch.black.gravel.entities.SecretIdentity;
import ch.black.gravel.repositories.ContractRepository;
import ch.black.gravel.services.CompanyService;
import ch.black.gravel.services.PersonService;

public class DataSetupRunner {

    public void run(
        PersonService personService, 
        CompanyService companyService, 
        ContractRepository contractRepository
    ) {
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
                LinkedHashMap<String, Object> subEntry = (LinkedHashMap<String, Object>) jsonEntry.get("secretIdentity");
                if (subEntry != null){
                    SecretIdentity localIdentity = new SecretIdentity(
                        (String) subEntry.get("secretName")
                    );
                    localEntry.setSecretIdentity(localIdentity);
                }
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
            System.out.println( "getCompanies() ERROR: " + e.getMessage());
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
					((BigInteger) jsonEntry.get("companyId")).longValue(),
					((BigInteger) jsonEntry.get("personId")).longValue()
				);
                entries.add(localEntry);
            }
        } catch (Exception e) {
            System.out.println( "createContracts() ERROR: " + e.getMessage());
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
