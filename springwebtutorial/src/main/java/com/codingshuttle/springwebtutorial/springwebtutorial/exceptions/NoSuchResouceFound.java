package com.codingshuttle.springwebtutorial.springwebtutorial.exceptions;

import java.util.NoSuchElementException;

public class NoSuchResouceFound  extends NoSuchElementException {

    public NoSuchResouceFound(String message){
        super(message);
    }
}
