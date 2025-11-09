package dev.meirong.showcase.initializr_demo.customizations;

import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class EditorConfigProjectContributor implements ProjectContributor {

    private final TemplateRenderer templateRenderer;

    public EditorConfigProjectContributor(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        Path editorconfigFile = projectRoot.resolve(".editorconfig");
        String editorconfigContent = this.templateRenderer.render(".editorconfig", null);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(editorconfigFile))) {
            writer.println(editorconfigContent);
        }
    }
}
