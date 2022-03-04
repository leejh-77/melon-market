package app.melon.web.controllers;

import app.melon.domain.services.UserService;
import app.melon.web.configs.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = { SecurityConfiguration.class, AuthController.class })
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService serviceMock;

    @Test
    public void register_emptyPayload_shouldFailAndReturn400() throws Exception {
        mvc.perform(post("/api/register"))
                .andExpect(status().is(400));
    }
}
