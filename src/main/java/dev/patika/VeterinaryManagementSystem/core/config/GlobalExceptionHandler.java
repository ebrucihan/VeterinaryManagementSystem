package dev.patika.VeterinaryManagementSystem.core.config;

import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.utilies.ResultHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for custom error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles NotFoundException and returns 404 status.
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Result> handleNotFoundException(NotFoundException e) {
        Result result = ResultHelper.notFoundError(e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles general exceptions and returns 500 status.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Result> handleGeneralException(Exception e) {
        Result result = ResultHelper.internalServerError(e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
