package com.javaweb.mystiacanteen.exception;

public class RedisCartParseException extends RuntimeException {
    public RedisCartParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
