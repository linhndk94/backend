package com.framework.backend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataAccessException extends RuntimeException {

    private HttpStatus httpStatus;
    private List<String> errors;

    public DataAccessException(String message) {
        super(message);
    }

    public static final DataAccessException notFound() {
        return notFound("Not found");
    }

    public static final DataAccessException notFound(String message, String... errors) {
        return notFound(message, Arrays.asList(errors));
    }

    public static final DataAccessException notFound(String message, List<String> errors) {
        DataAccessException dataAccessException = new DataAccessException(message);
        dataAccessException.setHttpStatus(HttpStatus.NOT_FOUND);
        dataAccessException.setErrors(errors);
        return dataAccessException;
    }

    public static final DataAccessException permissionDenied() {
        return permissionDenied("Permission denied");
    }

    public static final DataAccessException permissionDenied(String message, String... errors) {
        return permissionDenied(message, Arrays.asList(errors));
    }

    public static final DataAccessException permissionDenied(String message, List<String> errors) {
        DataAccessException dataAccessException = new DataAccessException(message);
        dataAccessException.setHttpStatus(HttpStatus.FORBIDDEN);
        dataAccessException.setErrors(errors);
        return dataAccessException;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
