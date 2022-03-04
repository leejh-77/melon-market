package app.melon.domain.services;

import app.melon.domain.models.user.User;
import app.melon.domain.models.user.UserRepository;
import app.melon.web.payloads.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void createUser(RegisterRequest request) {
        User user = new User(request.getUsername(), request.getEmailAddress(), request.getPassword());
        repository.save(user);
    }
}
