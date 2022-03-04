package app.melon.domain.errors;

public enum Errors {
    UsernameExists("Username already exists"),
    EmailAddressExists("Email address already exists");

    private String message;

    Errors(String message) {
        this.message = message;
    }

    public String message() {
        return this.message;
    }
}
