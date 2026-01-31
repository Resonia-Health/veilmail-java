package xyz.veilmail.sdk.exceptions;

import java.util.Map;

/**
 * Thrown when the rate limit is exceeded (429).
 */
public class RateLimitException extends VeilMailException {
    private final Integer retryAfter;

    public RateLimitException(String message) {
        this(message, null, null, null);
    }

    public RateLimitException(String message, Integer retryAfter, String errorCode, Map<String, Object> details) {
        super(message, errorCode, 429, details);
        this.retryAfter = retryAfter;
    }

    /**
     * Get the number of seconds to wait before retrying.
     */
    public Integer getRetryAfter() {
        return retryAfter;
    }
}
