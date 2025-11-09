package dev.meirong.showcase.initializr_demo.customizations;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class PluginCustomizer implements BuildCustomizer<MavenBuild> {
    @Override
    public void customize(MavenBuild mavenBuild) {
        mavenBuild
                .properties()
                .property("spotbugs-maven-plugin.version", "4.9.8.1")
                .property("spotbugs.version", "4.9.8")
                .property("spotless-maven-plugin.version", "3.0.0");

        mavenBuild.settings().finalName("app");

        // Plugins
        mavenBuild.plugins().add("org.springframework.boot", "spring-boot-maven-plugin");
        mavenBuild
                .plugins()
                .add(
                        "com.github.spotbugs",
                        "spotbugs-maven-plugin",
                        plugin -> {
                            plugin.version("${spotbugs-maven-plugin.version}");
                        });
        mavenBuild
                .plugins()
                .add(
                        "com.diffplug.spotless",
                        "spotless-maven-plugin",
                        plugin -> {
                            plugin.version("${spotless-maven-plugin.version}");
                        });
        mavenBuild
                .plugins()
                .add(
                        "io.github.git-commit-id",
                        "git-commit-id-maven-plugin");
        mavenBuild
                .plugins()
                .add(
                        "org.liquibase",
                        "liquibase-maven-plugin");
    }
}
