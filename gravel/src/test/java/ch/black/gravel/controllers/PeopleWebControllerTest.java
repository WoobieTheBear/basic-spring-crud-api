
package ch.black.gravel.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ch.black.gravel.GravelWebTestConfig;
import ch.black.gravel.daos.ArticleDAO;
import ch.black.gravel.daos.PetDAO;
import ch.black.gravel.daos.SecretIdentityDAO;
import ch.black.gravel.services.PersonService;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = GravelWebTestConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PeopleWebControllerTest {

    @Autowired
    private WebApplicationContext waContext;

    @Autowired
    private MockMvc mvc;

    @MockBean
    PersonService personService;

    @MockBean
    SecretIdentityDAO secretIdentityDAO;

    @MockBean
    PetDAO petDAO;

    @MockBean
    ArticleDAO articleDAO;

    protected MockMvc mockMvc;

    @BeforeAll
    public void setup() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(waContext).build();
    }

    @Test
    void testGetSearchFormFails() throws Exception {
        mvc.perform(
            get("/people/find")
            .param("search", "John Wayne")
            .param("action", "articles")
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "susan", authorities = {"USER", "POWER", "ADMIN"})
    void testGetSearchForm() throws Exception {
        mvc.perform(
            get("/people/find")
            .param("search", "John Wayne")
            .param("action", "articles")
        ).andExpect(status().isOk());
    }
}