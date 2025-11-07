package com.project_management.exception;

public class IllegalArgumentException extends RuntimeException{

    private String message;

    public IllegalArgumentException(){}

    public IllegalArgumentException(String message){
        super();
        this.message = message;
    }
}
