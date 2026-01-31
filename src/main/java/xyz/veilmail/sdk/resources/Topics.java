package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.Map;

/**
 * Subscription topic management.
 */
public class Topics {
    private final HttpClient http;

    public Topics(HttpClient http) {
        this.http = http;
    }

    public Map<String, Object> create(Map<String, Object> params) throws VeilMailException {
        return http.post("/v1/topics", params);
    }

    public Map<String, Object> list(Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/topics", params);
    }

    public Map<String, Object> list() throws VeilMailException {
        return http.get("/v1/topics");
    }

    public Map<String, Object> get(String id) throws VeilMailException {
        return http.get("/v1/topics/" + id);
    }

    public Map<String, Object> update(String id, Map<String, Object> params) throws VeilMailException {
        return http.patch("/v1/topics/" + id, params);
    }

    public void delete(String id) throws VeilMailException {
        http.delete("/v1/topics/" + id);
    }

    /**
     * Get a subscriber's topic preferences.
     */
    public Map<String, Object> getPreferences(String audienceId, String subscriberId) throws VeilMailException {
        return http.get("/v1/audiences/" + audienceId + "/subscribers/" + subscriberId + "/topics");
    }

    /**
     * Set a subscriber's topic preferences.
     */
    public Map<String, Object> setPreferences(String audienceId, String subscriberId,
                                               Map<String, Object> params) throws VeilMailException {
        return http.put(
                "/v1/audiences/" + audienceId + "/subscribers/" + subscriberId + "/topics",
                params
        );
    }
}
