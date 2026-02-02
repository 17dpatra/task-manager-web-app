package io.taskmanager.authentication.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenFilterException extends AuthenticationException {
    public TokenFilterException(Throwable cause) {
        super("", cause);
    }
}
