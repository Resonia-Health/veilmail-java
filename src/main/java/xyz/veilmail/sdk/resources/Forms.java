package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.Map;

/**
 * Signup form management.
 */
public class Forms {
    private final HttpClient http;

    public Forms(HttpClient http) {
        this.http = http;
    }

    public Map<String, Object> create(Map<String, Object> params) throws VeilMailException {
        return http.post("/v1/forms", params);
    }

    public Map<String, Object> list(Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/forms", params);
    }

    public Map<String, Object> list() throws VeilMailException {
        return http.get("/v1/forms");
    }

    public Map<String, Object> get(String id) throws VeilMailException {
        return http.get("/v1/forms/" + id);
    }

    public Map<String, Object> update(String id, Map<String, Object> params) throws VeilMailException {
        return http.put("/v1/forms/" + id, params);
    }

    public void delete(String id) throws VeilMailException {
        http.delete("/v1/forms/" + id);
    }
}
