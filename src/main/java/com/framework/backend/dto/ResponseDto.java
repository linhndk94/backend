package com.framework.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseDto<T> {

    @JsonIgnore
    private HttpStatus httpStatus;
    @JsonIgnore
    private HttpHeaders httpHeaders;
    private int code;
    private T data;
    private String message;
    private List<String> errors;

    public static <T> ResponseDto<T> build() {
        return new ResponseDto<>();
    }

    @PostConstruct
    private void init() {
        httpStatus = HttpStatus.OK;
        code = httpStatus.value();
    }

    public ResponseDto<T> withHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        return this;
    }

    public ResponseDto<T> withData(T data) {
        this.data = data;
        return this;
    }

    public ResponseDto<T> withHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public ResponseDto<T> withMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseDto<T> withErrors(String error) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        errors.add(error);
        return this;
    }

    public ResponseDto<T> withErrors(List<String> errors) {
        if (this.errors == null) {
            this.errors = new ArrayList<>(errors);
        } else {
            this.errors.addAll(errors);
        }
        return this;
    }

    public ResponseEntity<ResponseDto> toResponseEntity() {
        return new ResponseEntity<>(this, httpHeaders, httpStatus);
    }

}
