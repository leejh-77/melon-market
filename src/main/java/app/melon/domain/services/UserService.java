package app.melon.domain.services;

import app.melon.domain.errors.ApiException;
import app.melon.domain.errors.Errors;
import app.melon.domain.files.UserImageStorage;
import app.melon.domain.models.user.User;
import app.melon.domain.models.user.UserRepository;
import app.melon.web.payloads.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserImageStorage imageStorage;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, UserImageStorage imageStorage) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.imageStorage = imageStorage;
    }

    public void createUser(RegisterRequest request) throws ApiException {
        User user = this.repository.findByEmailAddress(request.getEmailAddress());
        if (user != null) {
            throw ApiException.of(Errors.UsernameExists);
        }
        String password = this.passwordEncoder.encode(request.getPassword());
        user = new User(request.getUsername(), request.getEmailAddress(), password);
        repository.save(user);
    }

    public void updateUserImage(long userId, MultipartFile file) throws ApiException {
        User user = this.repository.findById(userId);
        if (user == null) {
            throw ApiException.of(Errors.UserNotFound);
        }
        try {
            this.imageStorage.saveImage(userId, file);
        } catch (IOException e) {
            throw ApiException.of(Errors.ServerError);
        }
    }

    public byte[] getUserImage(long userId) throws ApiException {
        User user = this.repository.findById(userId);
        if (user == null) {
            throw ApiException.of(Errors.UserNotFound);
        }
        return this.imageStorage.loadImage(userId);
    }
}
