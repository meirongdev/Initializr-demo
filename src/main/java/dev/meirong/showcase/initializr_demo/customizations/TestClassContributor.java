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

import org.springframework.core.Ordered;

public class TestClassContributor implements ProjectContributor {

    private final ProjectDescription description;
    private final TemplateRenderer templateRenderer;

    public TestClassContributor(ProjectDescription description, TemplateRenderer templateRenderer) {
        this.description = description;
        this.templateRenderer = templateRenderer;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        Path testDir = projectRoot
                .resolve("src/test/java")
                .resolve(description.getPackageName().replace('.', '/'));

        if (!Files.exists(testDir)) {
            Files.createDirectories(testDir);
        }

        // 删除默认生成的 DemoApplicationTests.java
        Path defaultTestFile = testDir.resolve("DemoApplicationTests.java");
        if (Files.exists(defaultTestFile)) {
            Files.delete(defaultTestFile);
        }

        // 计算测试类名
        String artifactId = description.getArtifactId();
        String applicationName = capitalize(toCamelCase(artifactId)) + "Application";

        Map<String, Object> model = new HashMap<>();
        model.put("packageName", description.getPackageName());
        model.put("applicationName", applicationName);

        Path testFile = testDir.resolve(applicationName + "Tests.java");
        String content = this.templateRenderer.render("ApplicationTests.java", model);

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(testFile))) {
            writer.println(content);
        }

        // 生成 ContainerConfig.java
        Path containerConfigFile = testDir.resolve("ContainerConfig.java");
        String containerConfigContent = this.templateRenderer.render("ContainerConfig.java", model);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(containerConfigFile))) {
            writer.println(containerConfigContent);
        }
    }

    @Override
    public int getOrder() {
        // 设置为最低优先级，确保在默认测试类生成之后执行
        return Ordered.LOWEST_PRECEDENCE;
    }

    private String toCamelCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] parts = str.split("[-_]");
        StringBuilder result = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            result.append(capitalize(parts[i]));
        }
        return result.toString();
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
