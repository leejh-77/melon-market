package app.melon.domain.errors;

public class ApiException extends Exception {

    private final Errors type;

    private ApiException(Errors type) {
        super();
        this.type = type;
    }

    public static ApiException of(Errors type) {
        return new ApiException(type);
    }

    public String getErrorMessage() {
        return this.type.message();
    }
}
