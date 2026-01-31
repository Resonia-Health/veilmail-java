package com.example.auth.service;

import xyz.veilmail.sdk.VeilMailClient;
import xyz.veilmail.sdk.models.SendEmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeilMailService {

    private final VeilMailClient client;
    private final String fromEmail;
    private final String appUrl;

    public VeilMailService(
            @Value("${veilmail.api-key}") String apiKey,
            @Value("${veilmail.from-email:noreply@veilmail.xyz}") String fromEmail,
            @Value("${app.url:http://localhost:8080}") String appUrl) {
        this.client = new VeilMailClient(apiKey);
        this.fromEmail = fromEmail;
        this.appUrl = appUrl;
    }

    public void sendVerificationEmail(String email, String name, String token) {
        String url = appUrl + "/auth/verify-email?token=" + token;
        client.emails().send(new SendEmailRequest.Builder()
                .from(fromEmail)
                .to(email)
                .subject("Verify your email address")
                .html("<p>Hi " + name + ",</p><p>Click <a href=\"" + url + "\">here</a> to verify your email.</p>")
                .tags(List.of("auth", "verification"))
                .type("transactional")
                .build());
    }

    public void sendPasswordResetEmail(String email, String token) {
        String url = appUrl + "/auth/reset-password?token=" + token;
        client.emails().send(new SendEmailRequest.Builder()
                .from(fromEmail)
                .to(email)
                .subject("Reset your password")
                .html("<p>Click <a href=\"" + url + "\">here</a> to reset your password.</p>")
                .tags(List.of("auth", "password-reset"))
                .type("transactional")
                .build());
    }

    public void sendTwoFactorCode(String email, String code) {
        client.emails().send(new SendEmailRequest.Builder()
                .from(fromEmail)
                .to(email)
                .subject(code + " is your verification code")
                .html("<p>Your code: <strong>" + code + "</strong></p><p>Expires in 5 minutes.</p>")
                .tags(List.of("auth", "2fa"))
                .type("transactional")
                .build());
    }

    public void sendWelcomeEmail(String email, String name) {
        client.emails().send(new SendEmailRequest.Builder()
                .from(fromEmail)
                .to(email)
                .subject("Welcome!")
                .html("<p>Welcome, " + name + "! Your account is active.</p>")
                .tags(List.of("auth", "welcome"))
                .type("transactional")
                .build());
    }

    public void sendPasswordChangedEmail(String email) {
        client.emails().send(new SendEmailRequest.Builder()
                .from(fromEmail)
                .to(email)
                .subject("Your password was changed")
                .html("<p>Your password was changed. If you didn't do this, reset it immediately.</p>")
                .tags(List.of("auth", "security"))
                .type("transactional")
                .build());
    }

    public void sendTwoFactorToggledEmail(String email, boolean enabled) {
        String status = enabled ? "enabled" : "disabled";
        client.emails().send(new SendEmailRequest.Builder()
                .from(fromEmail)
                .to(email)
                .subject("Two-factor authentication " + status)
                .html("<p>2FA has been " + status + " on your account.</p>")
                .tags(List.of("auth", "2fa", "security"))
                .type("transactional")
                .build());
    }
}
