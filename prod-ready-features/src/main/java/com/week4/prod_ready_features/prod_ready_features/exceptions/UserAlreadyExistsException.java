package com.week4.prod_ready_features.prod_ready_features.exceptions;

public class UserAlreadyExistsException extends  RuntimeException{

    public UserAlreadyExistsException(String message){
        super(message);
    }
}
