package app.melon.domain.services;

import app.melon.domain.commands.RegisterCommand;
import app.melon.domain.commands.UpdateUserImageCommand;
import app.melon.domain.errors.ApiException;
import app.melon.domain.errors.Errors;
import app.melon.domain.files.UserImageStorage;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.models.user.User;
import app.melon.domain.models.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserImageStorage imageStorage;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, UserImageStorage imageStorage) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.imageStorage = imageStorage;
    }

    public User createUser(RegisterCommand command) throws ApiException {
        User user = this.repository.findByEmailAddress(command.getEmailAddress());
        if (user != null) {
            throw ApiException.of(Errors.UsernameExists);
        }
        String password = this.passwordEncoder.encode(command.getPassword());
        user = new User(command.getUsername(), command.getEmailAddress(), password);
        repository.save(user);
        return user;
    }

    public void updateUserImage(UpdateUserImageCommand command) throws ApiException {
        User user = this.repository.findById(command.getUserId());
        if (user == null) {
            throw ApiException.of(Errors.UserNotFound);
        }
        String imagePath = this.imageStorage.saveImage(command.getFile());
        user.setImagePath(imagePath);
        this.repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) {
            throw new UsernameNotFoundException("No user found");
        }

        User user = this.repository.findByEmailAddress(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found");
        }
        return new SimpleUser(user);
    }

    public User getUser(long userId) {
        return this.repository.findById(userId);
    }
}
