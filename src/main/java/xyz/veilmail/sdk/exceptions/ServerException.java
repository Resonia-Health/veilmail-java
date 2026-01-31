package xyz.veilmail.sdk.exceptions;

import java.util.Map;

/**
 * Thrown for server errors (5xx).
 */
public class ServerException extends VeilMailException {
    public ServerException(String message) {
        this(message, null, 500, null);
    }

    public ServerException(String message, String errorCode, Integer statusCode, Map<String, Object> details) {
        super(message, errorCode, statusCode, details);
    }
}
