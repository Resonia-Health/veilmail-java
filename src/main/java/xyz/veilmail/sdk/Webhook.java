package xyz.veilmail.sdk;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Utility for verifying webhook signatures.
 */
public final class Webhook {

    private Webhook() {}

    /**
     * Verify a webhook signature using constant-time HMAC-SHA256 comparison.
     *
     * @param body      The raw request body
     * @param signature The signature from the X-Signature-Hash header
     * @param secret    The webhook signing secret
     * @return true if the signature is valid
     *
     * <p>Example usage in a Spring controller:</p>
     * <pre>{@code
     * @PostMapping("/webhooks/veilmail")
     * public ResponseEntity<Void> handleWebhook(@RequestBody String body,
     *         @RequestHeader("X-Signature-Hash") String signature) {
     *     if (!Webhook.verifySignature(body, signature, webhookSecret)) {
     *         return ResponseEntity.status(401).build();
     *     }
     *     // Process event...
     *     return ResponseEntity.ok().build();
     * }
     * }</pre>
     */
    public static boolean verifySignature(String body, String signature, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(keySpec);
            byte[] hash = mac.doFinal(body.getBytes(StandardCharsets.UTF_8));
            String expected = bytesToHex(hash);

            return MessageDigest.isEqual(
                    expected.getBytes(StandardCharsets.UTF_8),
                    signature.getBytes(StandardCharsets.UTF_8)
            );
        } catch (Exception e) {
            return false;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
