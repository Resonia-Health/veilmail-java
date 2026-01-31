package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.Map;

/**
 * Contact property management.
 */
public class Properties {
    private final HttpClient http;

    public Properties(HttpClient http) {
        this.http = http;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> create(Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.post("/v1/properties", params);
        return unwrap(response);
    }

    public Map<String, Object> list(Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/properties", params);
    }

    public Map<String, Object> list() throws VeilMailException {
        return http.get("/v1/properties");
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> get(String id) throws VeilMailException {
        Map<String, Object> response = http.get("/v1/properties/" + id);
        return unwrap(response);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> update(String id, Map<String, Object> params) throws VeilMailException {
        Map<String, Object> response = http.patch("/v1/properties/" + id, params);
        return unwrap(response);
    }

    public void delete(String id) throws VeilMailException {
        http.delete("/v1/properties/" + id);
    }

    /**
     * Get a subscriber's property values.
     */
    public Map<String, Object> getValues(String audienceId, String subscriberId) throws VeilMailException {
        return http.get("/v1/audiences/" + audienceId + "/subscribers/" + subscriberId + "/properties");
    }

    /**
     * Set a subscriber's property values (merge with existing).
     * Pass null for a value to delete it.
     */
    public Map<String, Object> setValues(String audienceId, String subscriberId,
                                          Map<String, Object> values) throws VeilMailException {
        return http.put(
                "/v1/audiences/" + audienceId + "/subscribers/" + subscriberId + "/properties",
                values
        );
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> unwrap(Map<String, Object> response) {
        if (response.containsKey("data") && response.get("data") instanceof Map) {
            return (Map<String, Object>) response.get("data");
        }
        return response;
    }
}
