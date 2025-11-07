package com.project_management.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoDataExist extends RuntimeException{
    private String message;

    public NoDataExist(){}

    public NoDataExist(String message){
        super();
        this.message = message;
    }
}
