package ch.black.gravel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import ch.black.gravel.services.PersonService;

@SpringBootTest
@ContextConfiguration(classes = GravelWebTestConfig.class)
class GravelApplicationTests {

	@Autowired
    PersonService personService;

	@Test
	void contextLoads() {
		assertNotNull(personService);
	}

}
