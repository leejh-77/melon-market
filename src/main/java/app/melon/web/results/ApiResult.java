package app.melon.web.results;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ApiResult<T> {

    private final T body;
    private final int status;

    private ApiResult(int status) {
        this(status, null);
    }

    private ApiResult(int status, T body) {
        this.status = status;
        this.body = body;
    }

    public static ApiResult<?> created() {
        return new ApiResult<>(201);
    }

    public static ApiResult<MessageResult> failure(String message) {
        return message(403, message);
    }

    public static ApiResult<MessageResult> message(int status, String message) {
        return new ApiResult<>(status, new MessageResult(message));
    }

    public static ApiResult<?> ok() {
        return ok(null);
    }

    public static <T> ApiResult<T> ok(T body) {
        return new ApiResult<>(200, body);
    }

    public static ApiResult<MessageResult> notFound() {
        return message(404, "Page not found");
    }

    public static ApiResult<MessageResult> serverError() {
        return serverError("Server error");
    }

    public static ApiResult<MessageResult> serverError(String message) {
        return message(500, message);
    }

    public ResponseEntity<T> toResponse() {
        return ResponseEntity.status(this.status).body(this.body);
    }

    @Getter
    private static class MessageResult {
        private final String message;

        private MessageResult(String message) {
            this.message = message;
        }
    }
}
