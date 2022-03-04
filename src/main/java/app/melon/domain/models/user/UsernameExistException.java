package app.melon.domain.models.user;

public class UsernameExistException extends Exception {

    public UsernameExistException() {
        super("Duplicate username");
    }
}
