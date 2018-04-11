package com.framework.backend.controller.exception_controller;

import com.framework.backend.dto.ResponseDto;
import com.framework.backend.exception.BusinessException;
import com.framework.backend.exception.DataAccessException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity handleException(DataAccessException ex) {
        return ResponseDto.build().withHttpStatus(ex.getHttpStatus()).withMessage(ex.getMessage()).withErrors(ex.getErrors()).toResponseEntity();
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleException(BusinessException ex) {
        return ResponseDto.build().withHttpStatus(ex.getHttpStatus()).withMessage(ex.getMessage()).withErrors(ex.getErrors()).toResponseEntity();
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(ex.getClass().getName());
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return ResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST).withMessage(ex.getMessage()).withErrors(errors).withHttpHeaders(headers).toResponseEntity();
    }

    @Override
    protected ResponseEntity handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(ex.getClass().getName());
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return ResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST).withHttpHeaders(headers).withMessage(ex.getMessage()).withErrors(errors).toResponseEntity();
    }


    @Override
    protected ResponseEntity handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(ex.getClass().getName());
        String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
        return ResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST).withMessage(ex.getMessage()).withErrors(error).withHttpHeaders(headers).toResponseEntity();
    }

    @Override
    protected ResponseEntity handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(ex.getClass().getName());
        String error = ex.getRequestPartName() + " part is missing";
        return ResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST).withMessage(ex.getMessage()).withHttpHeaders(headers).withErrors(error).toResponseEntity();
    }

    @Override
    protected ResponseEntity handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(ex.getClass().getName());
        String error = ex.getParameterName() + " parameter is missing";
        return ResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST).withMessage(ex.getMessage()).withHttpHeaders(headers).withErrors(error).toResponseEntity();
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        logger.info(ex.getClass().getName());
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        return ResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST).withMessage(ex.getMessage()).withErrors(error).toResponseEntity();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        logger.info(ex.getClass().getName());
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        return ResponseDto.build().withHttpStatus(HttpStatus.BAD_REQUEST).withMessage(ex.getMessage()).withErrors(errors).toResponseEntity();
    }

    @Override
    protected ResponseEntity handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(ex.getClass().getName());
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        return ResponseDto.build().withHttpStatus(HttpStatus.NOT_FOUND).withMessage(ex.getMessage()).withHttpHeaders(headers).withErrors(error).toResponseEntity();
    }

    // 405

    @Override
    protected ResponseEntity handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(ex.getClass().getName());
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        builder.append(ex.getSupportedHttpMethods().stream().map(mediaType -> mediaType + " ").collect(Collectors.joining()));
        return ResponseDto.build().withHttpStatus(HttpStatus.METHOD_NOT_ALLOWED).withMessage(ex.getMessage()).withHttpHeaders(headers).withErrors(builder.toString()).toResponseEntity();
    }

    @Override
    protected ResponseEntity handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(ex.getClass().getName());
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        builder.append(ex.getSupportedMediaTypes().stream().map(mediaType -> mediaType + " ").collect(Collectors.joining()));
        return ResponseDto.build().withHttpStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE).withMessage(ex.getMessage()).withHttpHeaders(headers).withErrors(builder.substring(0, builder.length() - 2)).toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAll(Exception ex, WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);
        return ResponseDto.build().withHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(ex.getMessage()).withErrors("error occurs").toResponseEntity();
    }
}
