package ch.black.gravel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import ch.black.gravel.repositories.ContractRepository;
import ch.black.gravel.services.CompanyService;
import ch.black.gravel.services.PersonService;
import ch.black.util.DataSetupRunner;

@SpringBootApplication(
	scanBasePackages = {
		"ch.black.gravel",
		"ch.black.util"
	}
)
@EntityScan( 
	basePackages = {
		"ch.black.gravel",
		"ch.black.util"
	} 
)
public class GravelApplication {
	private DataSetupRunner setupRunner;

	public static void main(String[] args) {
		SpringApplication.run(GravelApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PersonService personService, CompanyService companyService, ContractRepository contractRepository){
		return runner -> {
			setupRunner = new DataSetupRunner();
			setupRunner.run(personService, companyService, contractRepository);
		};
	}

}
