package dev.meirong.showcase.initializr_demo.customizations;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class DependencyCustomizer implements BuildCustomizer<MavenBuild> {
    @Override
    public void customize(MavenBuild mavenBuild) {
        // spring boot parent

        // Web/基础
        mavenBuild.dependencies().add("web");
        mavenBuild.dependencies().add("actuator");
        mavenBuild.dependencies().add("validation");

        // Data Access
        mavenBuild.dependencies().add("jpa");
        mavenBuild.dependencies().add("mysql");
        mavenBuild.dependencies().add("liquibase");

        // Messaging
        mavenBuild.dependencies().add("kafka");
        mavenBuild.dependencies().add("kafka-json-schema");

        // Caching
        mavenBuild.dependencies().add("redis");

        // Observability
        mavenBuild.dependencies().add("prometheus");
        mavenBuild.dependencies().add("micrometer-tracing-bridge-otel");

        // Utilities
        mavenBuild.dependencies().add("devtools");
        mavenBuild.dependencies().add("logstash-logging");
        mavenBuild.dependencies().add("ulid");
        mavenBuild.dependencies().add("lombok");

        // Configuration
        mavenBuild.dependencies().add("configuration-processor");

        // Testing
        mavenBuild.dependencies().add("testcontainers");
        mavenBuild.dependencies().add("spring-boot-testcontainers");
        mavenBuild.dependencies().add("testcontainers-mysql");
        mavenBuild.dependencies().add("testcontainers-junit-jupiter");

    }
}
