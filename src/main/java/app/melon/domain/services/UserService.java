package app.melon.domain.services;

import app.melon.domain.errors.ApiException;
import app.melon.domain.errors.Errors;
import app.melon.domain.models.user.User;
import app.melon.domain.models.user.UserRepository;
import app.melon.web.payloads.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(RegisterRequest request) throws ApiException {
        User user = this.repository.findByEmailAddress(request.getEmailAddress());
        if (user != null) {
            throw ApiException.of(Errors.UsernameExists);
        }

        repository.save(user);
    }
}
