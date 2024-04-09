package ch.black.gravel;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.black.gravel.services.PersonService;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = GravelWebTestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
class GravelApplicationTests {

    @Autowired
    private MockMvc mvc;

	@Autowired
    PersonService personService;

	@Test
	void contextLoads() {
		assertNotNull(personService);
	}

    @Test
    void testGetRootPath() throws Exception {
        mvc.perform(
            get("/")
        ).andExpect(status().isOk());
    }
}
