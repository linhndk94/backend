package com.framework.backend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {

    private HttpStatus httpStatus;
    private List<String> errors;

    public BusinessException(String message) {
        super(message);
    }

    public static final BusinessException invalidParams() {
        return invalidParams("Not found");
    }

    public static final BusinessException invalidParams(String message, String... errors) {
        return invalidParams(message, Arrays.asList(errors));
    }

    public static final BusinessException invalidParams(String message, List<String> errors) {
        BusinessException businessException = new BusinessException(message);
        businessException.setHttpStatus(HttpStatus.BAD_REQUEST);
        businessException.setErrors(errors);
        return businessException;
    }

    public static final BusinessException internalServerError() {
        return internalServerError("Internal server error");
    }

    public static final BusinessException internalServerError(String message, String... errors) {
        return internalServerError(message, Arrays.asList(errors));
    }

    public static final BusinessException internalServerError(String message, List<String> errors) {
        BusinessException businessException = new BusinessException(message);
        businessException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        businessException.setErrors(errors);
        return businessException;
    }
}
