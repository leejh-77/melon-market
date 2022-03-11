package app.melon.web.controllers;

import app.melon.infrastructure.utils.JsonUtils;
import app.melon.domain.services.UserService;
import app.melon.web.configs.SecurityConfiguration;
import app.melon.web.requests.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {SecurityConfiguration.class, AuthController.class})
public class AuthControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Test
    public void emptyPayload_shouldFailAndReturn400() throws Exception {
        mvc.perform(post("/api/register"))
                .andExpect(status().is(400));
    }

    @Test
    public void invalidEmailAddress_shouldFailAndReturn400() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setEmailAddress("invalid---email");
        req.setUsername("Jonghoon");
        req.setPassword("12345667");

        mvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(req)))
                .andExpect(status().is(400));
    }

    @Test
    public void validRequest_shouldSuccessAndReturn201() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setEmailAddress("jonghoon.lee@email.com");
        req.setUsername("Jonghoon");
        req.setPassword("12345667");

        mvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(req)))
                .andDo(log())
                .andExpect(status().is(201));
    }
}
