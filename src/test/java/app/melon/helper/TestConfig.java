package app.melon.helper;

import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.models.user.User;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> new SimpleUser(User.create("email@sss.com", username, "password"));
    }

}
