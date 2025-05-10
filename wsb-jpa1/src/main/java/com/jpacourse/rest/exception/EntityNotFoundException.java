package com.jpacourse.rest.exception;

public class EntityNotFoundException extends RuntimeException
{

    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException(Long id) {
    }


}
