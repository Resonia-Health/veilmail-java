# Spring Boot Auth Example with VeilMail

Authentication email integration using the VeilMail Java SDK in a Spring Boot application.

## Key Files

- `src/main/java/com/example/auth/service/VeilMailService.java` - Mail service with auth email methods
- `src/main/resources/application.yml` - Configuration for VeilMail API key and app settings

## Setup

1. Add the VeilMail SDK dependency to your `pom.xml` or `build.gradle`
2. Copy the service and config files into your Spring Boot project
3. Set environment variables:
   ```bash
   export VEILMAIL_API_KEY=veil_live_your_key
   export VEILMAIL_FROM_EMAIL=noreply@yourdomain.com
   export APP_URL=https://yourdomain.com
   ```
4. Inject `VeilMailService` into your auth controllers

## Emails Covered

- Email verification
- Password reset
- Two-factor authentication codes
- Welcome email
- Password changed notification
- 2FA toggled notification
