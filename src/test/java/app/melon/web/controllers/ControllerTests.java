package app.melon.web.controllers;

import app.melon.infrastructure.utils.JsonUtils;
import app.melon.web.configs.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {SecurityConfiguration.class, ControllerTests.TestController.class})
public class ControllerTests {

    @Autowired
    private MockMvc mvc;

    @Controller
    static class TestController {

        @PostMapping("/hello")
        @ResponseBody
        public String greeting(@RequestBody TestRequest request, BindingResult result) {
            return "Hello, world!";
        }
    }

    @Test
    public void testGreeting() throws Exception {
        TestRequest request = new TestRequest();
//        request.setTitle("title");
//        request.setBody("body");

        mvc.perform(post("/hello")
                        .content(JsonUtils.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
