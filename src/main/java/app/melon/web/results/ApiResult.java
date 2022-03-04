package app.melon.web.results;

import app.melon.base.JsonUtils;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.HashMap;

public class ApiResult<T> {

    private String message;
    private T body;

    private ApiResult() {}

    public static ResponseEntity<ApiResult<?>> created() {
        return ResponseEntity.status(201).build();
    }

    public static ResponseEntity<ApiResult<?>> failure(String errorMessage) {
        return null;
    }
}
