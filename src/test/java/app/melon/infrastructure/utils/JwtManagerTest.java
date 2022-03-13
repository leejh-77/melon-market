package app.melon.infrastructure.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
//@ContextConfiguration(classes = {JwtManager.class})
public class JwtManagerTest {

    @Autowired
    private JwtManager jwtManager;

    @Test
    public void testJwt() {
        long id = 122;
        String jwt = jwtManager.generate(122);

        long extracted = jwtManager.extractId(jwt);
        assertEquals(id, extracted);
    }

}
