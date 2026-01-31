package xyz.veilmail.sdk;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import xyz.veilmail.sdk.exceptions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Internal HTTP client for communicating with the Veil Mail API.
 */
class HttpClient {
    static final String DEFAULT_BASE_URL = "https://api.veilmail.xyz";
    static final int DEFAULT_TIMEOUT_MS = 30_000;
    private static final String VERSION = "0.1.0";

    private final String apiKey;
    private final String baseUrl;
    private final int timeoutMs;
    private final ObjectMapper mapper;

    HttpClient(String apiKey, String baseUrl, int timeoutMs) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl != null ? baseUrl.replaceAll("/+$", "") : DEFAULT_BASE_URL;
        this.timeoutMs = timeoutMs > 0 ? timeoutMs : DEFAULT_TIMEOUT_MS;
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    }

    @SuppressWarnings("unchecked")
    Map<String, Object> get(String path) throws VeilMailException {
        return get(path, null);
    }

    @SuppressWarnings("unchecked")
    Map<String, Object> get(String path, Map<String, Object> query) throws VeilMailException {
        return request("GET", path, null, query);
    }

    Map<String, Object> post(String path) throws VeilMailException {
        return post(path, null);
    }

    Map<String, Object> post(String path, Map<String, Object> body) throws VeilMailException {
        return request("POST", path, body, null);
    }

    Map<String, Object> patch(String path, Map<String, Object> body) throws VeilMailException {
        return request("PATCH", path, body, null);
    }

    Map<String, Object> put(String path, Map<String, Object> body) throws VeilMailException {
        return request("PUT", path, body, null);
    }

    Map<String, Object> delete(String path) throws VeilMailException {
        return request("DELETE", path, null, null);
    }

    String getRaw(String path, Map<String, Object> query) throws VeilMailException {
        try {
            String url = buildUrl(path, query);
            HttpURLConnection conn = createConnection(url, "GET");

            int status = conn.getResponseCode();
            if (status >= 400) {
                String responseBody = readStream(conn.getErrorStream());
                throwApiError(status, responseBody);
            }

            return readStream(conn.getInputStream());
        } catch (VeilMailException e) {
            throw e;
        } catch (IOException e) {
            throw new VeilMailException("Network error: " + e.getMessage());
        }
    }

    ObjectMapper getMapper() {
        return mapper;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> request(String method, String path, Map<String, Object> body,
                                         Map<String, Object> query) throws VeilMailException {
        try {
            String url = buildUrl(path, query);
            HttpURLConnection conn = createConnection(url, method);

            if (body != null && ("POST".equals(method) || "PATCH".equals(method) || "PUT".equals(method))) {
                Map<String, Object> filtered = filterNulls(body);
                byte[] jsonBytes = mapper.writeValueAsBytes(filtered);
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(jsonBytes);
                }
            }

            int status = conn.getResponseCode();

            if (status == 204) {
                return Collections.emptyMap();
            }

            String responseBody;
            if (status >= 400) {
                responseBody = readStream(conn.getErrorStream());
                throwApiError(status, responseBody);
                return Collections.emptyMap(); // unreachable
            }

            responseBody = readStream(conn.getInputStream());
            if (responseBody == null || responseBody.isEmpty()) {
                return Collections.emptyMap();
            }

            return mapper.readValue(responseBody, Map.class);
        } catch (VeilMailException e) {
            throw e;
        } catch (IOException e) {
            throw new VeilMailException("Network error: " + e.getMessage());
        }
    }

    private HttpURLConnection createConnection(String url, String method) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod(method);
        conn.setConnectTimeout(timeoutMs);
        conn.setReadTimeout(timeoutMs);
        conn.setRequestProperty("Authorization", "Bearer " + apiKey);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "veilmail-java/" + VERSION);
        return conn;
    }

    private String buildUrl(String path, Map<String, Object> query) {
        StringBuilder sb = new StringBuilder(baseUrl).append(path);

        if (query != null && !query.isEmpty()) {
            String qs = query.entrySet().stream()
                    .filter(e -> e.getValue() != null)
                    .map(e -> {
                        String key = URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8);
                        String val = URLEncoder.encode(String.valueOf(e.getValue()), StandardCharsets.UTF_8);
                        return key + "=" + val;
                    })
                    .collect(Collectors.joining("&"));
            if (!qs.isEmpty()) {
                sb.append('?').append(qs);
            }
        }

        return sb.toString();
    }

    private Map<String, Object> filterNulls(Map<String, Object> data) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            if (entry.getValue() instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nested = (Map<String, Object>) entry.getValue();
                result.put(entry.getKey(), filterNulls(nested));
            } else {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    private String readStream(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        try (InputStream is = stream) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    @SuppressWarnings("unchecked")
    private void throwApiError(int statusCode, String responseBody) throws VeilMailException {
        String message = "HTTP error " + statusCode;
        String code = null;
        Map<String, Object> details = null;
        List<String> piiTypes = null;
        Integer retryAfter = null;

        try {
            Map<String, Object> data = mapper.readValue(responseBody, Map.class);
            Map<String, Object> error = data.containsKey("error")
                    ? (Map<String, Object>) data.get("error")
                    : data;

            if (error.containsKey("message")) {
                message = (String) error.get("message");
            }
            if (error.containsKey("code")) {
                code = (String) error.get("code");
            }
            if (error.containsKey("details")) {
                details = (Map<String, Object>) error.get("details");
            }
            if (error.containsKey("piiTypes")) {
                piiTypes = (List<String>) error.get("piiTypes");
            }
            if (error.containsKey("retryAfter")) {
                retryAfter = ((Number) error.get("retryAfter")).intValue();
            }
        } catch (Exception ignored) {
            // Use defaults
        }

        switch (statusCode) {
            case 400:
                throw new ValidationException(message, code, details);
            case 401:
                throw new AuthenticationException(message, code, details);
            case 403:
                throw new ForbiddenException(message, code, details);
            case 404:
                throw new NotFoundException(message, code, details);
            case 422:
                if ("pii_detected".equals(code) || piiTypes != null) {
                    throw new PiiDetectedException(message, piiTypes, code, details);
                }
                throw new ValidationException(message, code, details);
            case 429:
                throw new RateLimitException(message, retryAfter, code, details);
            default:
                if (statusCode >= 500) {
                    throw new ServerException(message, code, statusCode, details);
                }
                throw new VeilMailException(message, code, statusCode, details);
        }
    }
}
