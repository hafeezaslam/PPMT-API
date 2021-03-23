package com.hafeez.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
// @RestController
// TODO: Understand
//  @RestController why needed?
//  why extending from ResponseEntityExceptionHandler
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<ProjectIdExceptionResponse> handleException(ProjectIdException exc) {
        ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(exc.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<ProjectNotFoundExceptionResponse> handleException(ProjectNotFoundException exc) {
        ProjectNotFoundExceptionResponse exceptionResponse = new ProjectNotFoundExceptionResponse(exc.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<UsernameAlreadyExistsResponse> handleException(UsernameAlreadyExistsException exc) {
        UsernameAlreadyExistsResponse exceptionResponse = new UsernameAlreadyExistsResponse(exc.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
