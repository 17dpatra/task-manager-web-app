package io.taskmanager.authentication.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String username) {
        super(username + " already exists");

    }
}
