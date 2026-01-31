package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.Map;

/**
 * Audience management.
 */
public class Audiences {
    private final HttpClient http;

    public Audiences(HttpClient http) {
        this.http = http;
    }

    /**
     * Create a new audience.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> create(Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/audiences", params);
        return unwrap(response);
    }

    /**
     * List all audiences.
     */
    public Map<String, Object> list(Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/audiences", params);
    }

    public Map<String, Object> list() throws VeilMailException {
        return http.get("/v1/audiences");
    }

    /**
     * Get a single audience by ID.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> get(String id) throws VeilMailException {
        Map<String, Object> response = http.get("/v1/audiences/" + id);
        return unwrap(response);
    }

    /**
     * Update an audience.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> update(String id, Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.put("/v1/audiences/" + id, params);
        return unwrap(response);
    }

    /**
     * Delete an audience.
     */
    public void delete(String id) throws VeilMailException {
        http.delete("/v1/audiences/" + id);
    }

    /**
     * Get a Subscribers helper scoped to the given audience.
     */
    public Subscribers subscribers(String audienceId) {
        return new Subscribers(http, audienceId);
    }

    /**
     * Recalculate engagement scores for all subscribers.
     */
    public Map<String, Object> recalculateEngagement(String audienceId) throws VeilMailException {
        return http.post("/v1/audiences/" + audienceId + "/recalculate-engagement", Map.of());
    }

    /**
     * Get engagement statistics for an audience.
     */
    public Map<String, Object> getEngagementStats(String audienceId) throws VeilMailException {
        return http.get("/v1/audiences/" + audienceId + "/engagement-stats");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> unwrap(Map<String, Object> response) {
        if (response.containsKey("data") && response.get("data") instanceof Map) {
            return (Map<String, Object>) response.get("data");
        }
        return response;
    }
}
