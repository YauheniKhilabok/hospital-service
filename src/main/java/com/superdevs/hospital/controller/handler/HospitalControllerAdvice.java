package com.superdevs.hospital.controller.handler;

import com.superdevs.hospital.exception.InvalidDateRangeException;
import com.superdevs.hospital.exception.InvalidUUIDException;
import com.superdevs.hospital.exception.PatientNotFoundException;
import com.superdevs.hospital.exception.StaffMemberNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class HospitalControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return buildErrorResponse("Server error. Something is wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        log.error(ex.getMessage(), ex);
        return buildErrorResponse(
            "The header should not be empty and content type should be application/json",
            HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error(ex.getMessage(), ex);
        return buildErrorResponse("This method is not allowed", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors().stream()
            .map(fieldError -> new FieldError(fieldError.getField(), fieldError.getDefaultMessage()))
            .collect(Collectors.toList());
        FieldErrorResponse errorResponse = FieldErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .code(HttpStatus.BAD_REQUEST.value())
            .message("Method arguments are not valid")
            .fieldErrors(fieldErrors)
            .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
        ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        List<FieldError> parameterErrors = ex.getConstraintViolations().stream()
            .map(violation -> new FieldError(violation.getPropertyPath().toString(), violation.getMessage()))
            .collect(Collectors.toList());
        FieldErrorResponse errorResponse = FieldErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .code(HttpStatus.BAD_REQUEST.value())
            .message("Method arguments are not valid")
            .fieldErrors(parameterErrors)
            .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUUIDException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUUIDException(InvalidUUIDException ex) {
        log.error(ex.getMessage(), ex);
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StaffMemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStaffMemberNotFoundException(StaffMemberNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDateRangeException(InvalidDateRangeException ex) {
        log.error(ex.getMessage(), ex);
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientNotFoundException(PatientNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), httpStatus.value(), message);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
