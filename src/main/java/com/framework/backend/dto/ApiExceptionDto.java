package com.framework.backend.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ApiExceptionDto {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiExceptionDto(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiExceptionDto(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(error);
    }

    public ApiExceptionDto(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiExceptionDto(String message) {
        this.message = message;
    }

    public void addError(@NotNull String error) {
        if (errors == null) errors = new ArrayList<>();
        errors.add(error);
    }

}
