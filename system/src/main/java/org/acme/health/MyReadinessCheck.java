package org.acme;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

@Readiness
public class MyReadinessCheck implements HealthCheck{

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder response = HealthCheckResponse.named("Database Connection Health Check");

        response.up().withData("key", "8001");

        return response.build();
    }
    
} MyReadinessCheck {
    
}
