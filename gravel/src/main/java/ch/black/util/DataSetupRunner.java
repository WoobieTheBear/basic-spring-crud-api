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

import ch.black.gravel.daos.ArticleDAO;
import ch.black.gravel.entities.Article;
import ch.black.gravel.entities.Company;
import ch.black.gravel.entities.Contract;
import ch.black.gravel.entities.Person;
import ch.black.gravel.entities.SecretIdentity;
import ch.black.gravel.entities.Pet;
import ch.black.gravel.repositories.ContractRepository;
import ch.black.gravel.services.CompanyService;
import ch.black.gravel.services.PersonService;

public class DataSetupRunner {

    public void run(
        PersonService personService, 
        CompanyService companyService, 
        ContractRepository contractRepository,
        ArticleDAO articleDAO
    ) {

        addPeople(personService);
        addCompanies(companyService);
        addContracts(personService, companyService, contractRepository);
        addArticles(personService, articleDAO);
    }

    private void addPeople(PersonService personService) {
		List<Person> people = personService.findAll();
		if (people.isEmpty()) {
			people = getPeople();
            for (Person person : people) {
				personService.save(person);
				System.out.println("[INFO]: Person created " + person);
			}
		}
    }

    private void addCompanies(CompanyService companyService) {
		List<Company> companies = companyService.findAll();
		if (companies.isEmpty()) {
			companies = getCompanies();
			for (Company company : companies) {
				companyService.save(company);
				System.out.println("[INFO]: Company created " + company);
			}
		}
    }

    private void addContracts(
        PersonService personService, 
        CompanyService companyService, 
        ContractRepository contractRepository
    ) {
		List<Contract> contracts = contractRepository.findAll();
		if (contracts.isEmpty()) {
			contracts = setupContracts(personService, companyService);
			for (Contract contract : contracts) {
				contractRepository.save(contract);
				System.out.println("[INFO]: Contract created " + contract);
			}
		}
    }

    private void addArticles(PersonService personService, ArticleDAO articleDAO) {
        List<Article> articles = articleDAO.findAll();
        if (articles.isEmpty()) {
            setupArticles(personService, articleDAO);
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
                LinkedHashMap<String, Object> secretIdentityEntry = (LinkedHashMap<String, Object>) jsonEntry.get("secretIdentity");
                if (secretIdentityEntry != null){
                    SecretIdentity localIdentity = new SecretIdentity(
                        (String) secretIdentityEntry.get("secretName")
                    );
                    localEntry.setSecretIdentity(localIdentity);
                }
                ArrayList<LinkedHashMap<String, Object>> petsEntry = (ArrayList<LinkedHashMap<String, Object>>) jsonEntry.get("pets");
                if (petsEntry != null){
                    for (LinkedHashMap<String, Object> petEntry : petsEntry) {
                        Pet localPet = new Pet(
                            (String) petEntry.get("name"),
                            (String) petEntry.get("species")
                        );
                        localEntry.addPet(localPet);
                    }
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

	private List<Contract> setupContracts(PersonService personService, CompanyService companyService){
        List<Contract> entries = new ArrayList<>();
        try {
            List<LinkedHashMap<String, Object>> data = readTestData("contracts");
            for (LinkedHashMap<String, Object> jsonEntry : data) {
                Contract localEntry = new Contract(
                    (String) jsonEntry.get("title"),
					((BigInteger) jsonEntry.get("contractWorkload")).intValue(),
					((BigInteger) jsonEntry.get("salary")).intValue()
				);

                Person person = personService.findById(((BigInteger) jsonEntry.get("personId")).longValue());
                Company company = companyService.findById(((BigInteger) jsonEntry.get("companyId")).longValue());

                localEntry.setPerson(person);
                localEntry.setCompany(company);
                entries.add(localEntry);
            }
        } catch (Exception e) {
            System.out.println( "createContracts() ERROR: " + e.getMessage());
        }
        return entries;
	}

	private void setupArticles(PersonService personService, ArticleDAO articleDAO){
        try {
            List<LinkedHashMap<String, Object>> data = readTestData("articles");
            for (LinkedHashMap<String, Object> jsonEntry : data) {
                Article localEntry = new Article(
					(String) jsonEntry.get("content")
				);
                Article persistedEntry = articleDAO.save(localEntry);

                ArrayList<BigInteger> authorIds = (ArrayList<BigInteger>) jsonEntry.get("authors");
                if (authorIds != null){
                    for (BigInteger authorId : authorIds) {
                        Person localAuthor = personService.loadArticlesEagerByPersonId(authorId.longValue());
                        if (localAuthor != null) {
                            persistedEntry.addAuthor(localAuthor);
                        }
                    }
                }
                articleDAO.update(persistedEntry);
				System.out.println("[INFO]: Article created " + persistedEntry);
            }
        } catch (Exception e) {
            System.out.println( "setupArticles() ERROR: " + e.getMessage());
        }
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
