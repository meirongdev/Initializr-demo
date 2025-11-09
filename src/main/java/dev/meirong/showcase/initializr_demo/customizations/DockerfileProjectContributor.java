package dev.meirong.showcase.initializr_demo.customizations;

import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class DockerfileProjectContributor implements ProjectContributor {
    private final ProjectDescription description;
    private final TemplateRenderer templateRenderer;

    public DockerfileProjectContributor(
            ProjectDescription description, TemplateRenderer templateRenderer) {
        this.description = description;
        this.templateRenderer = templateRenderer;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        Path dockerfile = projectRoot.resolve("Dockerfile");

        Map<String, String> model = Map.of("artifactId", this.description.getArtifactId());
        String dockerfileContent = this.templateRenderer.render("Dockerfile", model);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(dockerfile))) {
            writer.println(dockerfileContent);
        }
    }
}
