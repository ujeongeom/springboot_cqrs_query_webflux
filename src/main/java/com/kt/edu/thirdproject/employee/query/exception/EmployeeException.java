package com.kt.edu.thirdproject.employee.query.exception;

import org.springframework.http.HttpStatus;

public class EmployeeException extends Exception{
    private HttpStatus httpStatus;

    public EmployeeException(String message) {
        this(message, HttpStatus.EXPECTATION_FAILED); //417
    }

    public EmployeeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {

        return httpStatus;
    }
}