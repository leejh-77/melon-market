package app.melon.web.results;

import lombok.Getter;
import org.apache.coyote.Response;
import org.aspectj.bridge.Message;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ApiResult {

    public static ResponseEntity<?> created() {
        return build(201, null);
    }

    public static ResponseEntity<MessageResult> failure(String message) {
        return message(400, message);
    }

    private static <T> ResponseEntity<T> build(int status, T body) {
        return ResponseEntity.status(status).body(body);
    }

    public static ResponseEntity<MessageResult> message(int status, String message) {
        return build(status, new MessageResult(message));
    }

    public static ResponseEntity<?> ok() {
        return ok(null);
    }

    public static <T> ResponseEntity<T> ok(T body) {
        return build(200, body);
    }

    public static ResponseEntity<MessageResult> unauthorized() {
        return message(401, "Unauthorized");
    }

    public static ResponseEntity<MessageResult> notFound() {
        return message(404, "Page not found");
    }

    public static ResponseEntity<MessageResult> badRequest() {
        return message(400, "Bad request");
    }

    public static ResponseEntity<MessageResult> serverError() {
        return serverError("Server error");
    }

    public static ResponseEntity<MessageResult> serverError(String message) {
        return message(500, message);
    }

    @Getter
    private static class MessageResult {
        private final String message;

        private MessageResult(String message) {
            this.message = message;
        }
    }
}
