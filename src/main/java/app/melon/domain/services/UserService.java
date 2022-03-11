package app.melon.domain.services;

import app.melon.domain.commands.RegisterCommand;
import app.melon.domain.commands.UpdateUserImageCommand;
import app.melon.domain.errors.ApiException;
import app.melon.domain.errors.Errors;
import app.melon.domain.files.ImageStorage;
import app.melon.domain.models.user.SimpleUser;
import app.melon.domain.models.user.User;
import app.melon.infrastructure.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final ImageStorage imageStorage;

    public UserService(UserRepository repository, @Qualifier("UserImageStorage") ImageStorage imageStorage) {
        this.repository = repository;
        this.imageStorage = imageStorage;
    }

    public User registerUser(RegisterCommand command) throws ApiException {
        User user = this.repository.findByEmailAddress(command.getEmailAddress());
        if (user != null) {
            throw ApiException.of(Errors.UsernameExists);
        }
        PasswordEncoder encoder = this.passwordEncoder();
        String password = encoder.encode(command.getPassword());
        user = User.create(command.getEmailAddress(), command.getUsername(), password);
        repository.save(user);
        return user;
    }

    public void updateUserImage(UpdateUserImageCommand command) throws ApiException {
        Optional<User> opUser = this.repository.findById(command.getUserId());
        if (opUser.isEmpty()) {
            throw ApiException.of(Errors.UserNotFound);
        }
        User user = opUser.get();
        String imagePath = this.imageStorage.saveImage(command.getFile());
        user.setImageUrl(imagePath);
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
        return this.repository.findById(userId).get();
    }

    @Lookup
    public PasswordEncoder passwordEncoder() {
        return null;
    }

}
