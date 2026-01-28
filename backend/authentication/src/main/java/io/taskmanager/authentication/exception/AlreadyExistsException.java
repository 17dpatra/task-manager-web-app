package io.taskmanager.authentication.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(Object object) {
        super(object + " already exists");
    }

}
