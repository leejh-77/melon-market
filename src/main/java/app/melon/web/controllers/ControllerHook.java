package app.melon.web.controllers;

import app.melon.domain.errors.ApiException;
import app.melon.web.results.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerHook {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleException(ApiException e) {
        return ApiResult.failure(e.getErrorMessage());
    }
}
