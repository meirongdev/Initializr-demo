package dev.meirong.showcase.initializr_demo.customizations;

import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link ProjectContributor} that deletes the default {@code application.properties} and creates
 * an {@code application.yaml} from a template.
 */
public class YamlConfigurationCustomizer implements ProjectContributor {

    private final ProjectDescription description;
    private final TemplateRenderer templateRenderer;

    public YamlConfigurationCustomizer(ProjectDescription description, TemplateRenderer templateRenderer) {
        this.description = description;
        this.templateRenderer = templateRenderer;
    }


    @Override
    public void contribute(Path projectRoot) throws IOException {
        Path resourcesDir = projectRoot.resolve("src/main/resources");
        // 确保父目录存在
        if (!Files.exists(resourcesDir)) {
            Files.createDirectories(resourcesDir);
        }
        Path propertiesFile = resourcesDir.resolve("application.properties");

        if (Files.exists(propertiesFile)) {
            Files.delete(propertiesFile);
        }

        Path yamlFile = resourcesDir.resolve("application.yaml");
        // create file if not exists
        if (!Files.exists(yamlFile)) {
            Files.createFile(yamlFile);
        }

        String artifactId = description.getArtifactId();
        Map<String, String> model = new HashMap<>();
        model.put("artifactId", artifactId);
        String yamlContent = this.templateRenderer.render("application.yaml", model);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(yamlFile))) {
            writer.println(yamlContent);
        }
    }
}
