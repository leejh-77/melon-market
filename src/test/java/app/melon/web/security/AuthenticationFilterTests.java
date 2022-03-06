package app.melon.web.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class AuthenticationFilterTests {

    @MockBean
    private AuthenticationManager authenticationManagerMock;

    @Test
    public void validJsonStringRequestBody_shouldSucceed() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/login");
        request.setContent("{\"username\": \"testusername\", \"password\": \"TestPassword!\"}".getBytes());
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerMock);
        filter.attemptAuthentication(request, new MockHttpServletResponse());
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken("testusername", "TestPassword!");
        verify(authenticationManagerMock).authenticate(token);
    }
}
