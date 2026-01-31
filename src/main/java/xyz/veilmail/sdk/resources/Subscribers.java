package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.Map;

/**
 * Subscriber management within an audience.
 */
public class Subscribers {
    private final HttpClient http;
    private final String basePath;

    Subscribers(HttpClient http, String audienceId) {
        this.http = http;
        this.basePath = "/v1/audiences/" + audienceId + "/subscribers";
    }

    /**
     * List subscribers with optional filters.
     */
    public Map<String, Object> list(Map<String, Object> params) throws VeilMailException {
        return http.get(basePath, params);
    }

    public Map<String, Object> list() throws VeilMailException {
        return http.get(basePath);
    }

    /**
     * Add a subscriber.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> add(Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.post(basePath, params);
        return unwrap(response);
    }

    /**
     * Get a single subscriber by ID.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> get(String subscriberId) throws VeilMailException {
        Map<String, Object> response = http.get(basePath + "/" + subscriberId);
        return unwrap(response);
    }

    /**
     * Update a subscriber.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> update(String subscriberId, Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.put(basePath + "/" + subscriberId, params);
        return unwrap(response);
    }

    /**
     * Remove a subscriber.
     */
    public void remove(String subscriberId) throws VeilMailException {
        http.delete(basePath + "/" + subscriberId);
    }

    /**
     * Confirm a double opt-in subscriber.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> confirm(String subscriberId) throws VeilMailException {
        Map<String, Object> response = http.post(basePath + "/" + subscriberId + "/confirm");
        return unwrap(response);
    }

    /**
     * Bulk import subscribers.
     */
    public Map<String, Object> importSubscribers(Map<String, Object> params) throws VeilMailException {
        return http.post(basePath + "/import", params);
    }

    /**
     * Export subscribers as CSV.
     */
    public String export(Map<String, Object> params) throws VeilMailException {
        return http.getRaw(basePath + "/export", params);
    }

    public String export() throws VeilMailException {
        return http.getRaw(basePath + "/export", null);
    }

    /**
     * Get a subscriber's activity timeline.
     */
    public Map<String, Object> activity(String subscriberId, Map<String, Object> params) throws VeilMailException {
        return http.get(basePath + "/" + subscriberId + "/activity", params);
    }

    public Map<String, Object> activity(String subscriberId) throws VeilMailException {
        return http.get(basePath + "/" + subscriberId + "/activity");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> unwrap(Map<String, Object> response) {
        if (response.containsKey("data") && response.get("data") instanceof Map) {
            return (Map<String, Object>) response.get("data");
        }
        return response;
    }
}
