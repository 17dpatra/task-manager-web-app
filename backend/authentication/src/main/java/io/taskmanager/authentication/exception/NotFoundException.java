package io.taskmanager.authentication.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Object entity) {
        super(entity + " not found");
    }
}
