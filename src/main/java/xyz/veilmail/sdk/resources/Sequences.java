package xyz.veilmail.sdk.resources;

import xyz.veilmail.sdk.HttpClient;
import xyz.veilmail.sdk.exceptions.VeilMailException;

import java.util.List;
import java.util.Map;

/**
 * Automation sequence management.
 */
public class Sequences {
    private final HttpClient http;

    public Sequences(HttpClient http) {
        this.http = http;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> create(Map<String, Object> params) throws VeilMailException {
        return http.post("/v1/sequences", params);
    }

    public Map<String, Object> list(Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/sequences", params);
    }

    public Map<String, Object> list() throws VeilMailException {
        return http.get("/v1/sequences");
    }

    public Map<String, Object> get(String id) throws VeilMailException {
        return http.get("/v1/sequences/" + id);
    }

    public Map<String, Object> update(String id, Map<String, Object> params) throws VeilMailException {
        return http.put("/v1/sequences/" + id, params);
    }

    public void delete(String id) throws VeilMailException {
        http.delete("/v1/sequences/" + id);
    }

    public Map<String, Object> activate(String id) throws VeilMailException {
        return http.post("/v1/sequences/" + id + "/activate");
    }

    public Map<String, Object> pause(String id) throws VeilMailException {
        return http.post("/v1/sequences/" + id + "/pause");
    }

    public Map<String, Object> archive(String id) throws VeilMailException {
        return http.post("/v1/sequences/" + id + "/archive");
    }

    public Map<String, Object> addStep(String sequenceId, Map<String, Object> params) throws VeilMailException {
        return http.post("/v1/sequences/" + sequenceId + "/steps", params);
    }

    public Map<String, Object> updateStep(String sequenceId, String stepId, Map<String, Object> params) throws VeilMailException {
        return http.put("/v1/sequences/" + sequenceId + "/steps/" + stepId, params);
    }

    public void deleteStep(String sequenceId, String stepId) throws VeilMailException {
        http.delete("/v1/sequences/" + sequenceId + "/steps/" + stepId);
    }

    public void reorderSteps(String sequenceId, List<Map<String, Object>> steps) throws VeilMailException {
        http.post("/v1/sequences/" + sequenceId + "/steps/reorder", Map.of("steps", steps));
    }

    public Map<String, Object> enroll(String sequenceId, List<String> subscriberIds) throws VeilMailException {
        return http.post("/v1/sequences/" + sequenceId + "/enroll", Map.of("subscriberIds", subscriberIds));
    }

    public Map<String, Object> listEnrollments(String sequenceId, Map<String, Object> params) throws VeilMailException {
        return http.get("/v1/sequences/" + sequenceId + "/enrollments", params);
    }

    public Map<String, Object> listEnrollments(String sequenceId) throws VeilMailException {
        return http.get("/v1/sequences/" + sequenceId + "/enrollments");
    }

    public void removeEnrollment(String sequenceId, String enrollmentId) throws VeilMailException {
        http.delete("/v1/sequences/" + sequenceId + "/enrollments/" + enrollmentId);
    }
}
