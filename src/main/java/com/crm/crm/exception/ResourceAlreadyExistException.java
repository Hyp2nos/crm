package com.crm.crm.exception;

public class ResourceAlreadyExistException extends IllegalArgumentException{
    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
