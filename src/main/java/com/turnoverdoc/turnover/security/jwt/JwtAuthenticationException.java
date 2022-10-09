package com.turnoverdoc.turnover.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
