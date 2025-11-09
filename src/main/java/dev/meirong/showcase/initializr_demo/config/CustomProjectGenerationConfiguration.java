package dev.meirong.showcase.initializr_demo.config;

import dev.meirong.showcase.initializr_demo.customizations.CustomStructureContributor;
import dev.meirong.showcase.initializr_demo.customizations.DependencyCustomizer;
import dev.meirong.showcase.initializr_demo.customizations.DockerfileProjectContributor;
import dev.meirong.showcase.initializr_demo.customizations.EditorConfigProjectContributor;
import dev.meirong.showcase.initializr_demo.customizations.GitInitProjectContributor;
import dev.meirong.showcase.initializr_demo.customizations.PluginCustomizer;
import dev.meirong.showcase.initializr_demo.customizations.TestClassContributor;
import dev.meirong.showcase.initializr_demo.customizations.YamlConfigurationCustomizer;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.io.template.TemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.spring.scm.git.GitIgnoreCustomizer;

import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class CustomProjectGenerationConfiguration {

    private final ProjectDescription description;

    public CustomProjectGenerationConfiguration(ProjectDescription description) {
        this.description = description;
    }


    @Bean
    ProjectContributor gitInitProjectContributor() {
        return new GitInitProjectContributor();
    }

    @Bean
    BuildCustomizer<MavenBuild> dependencyCustomizer() {
        return new DependencyCustomizer();
    }

    @Bean
    BuildCustomizer<MavenBuild> pluginCustomizer() {
        return new PluginCustomizer();
    }

    @Bean
    YamlConfigurationCustomizer yamlConfigurationCustomizer(TemplateRenderer templateRenderer) {
        return new YamlConfigurationCustomizer(description, templateRenderer);
    }

    @Bean
    ProjectContributor editorConfigProjectContributor(TemplateRenderer templateRenderer) {
        return new EditorConfigProjectContributor(templateRenderer);
    }

    @Bean
    ProjectContributor dockerfileProjectContributor(
            ProjectDescription description, TemplateRenderer templateRenderer) {
        return new DockerfileProjectContributor(description, templateRenderer);
    }

    @Bean
    CustomStructureContributor customStructureContributor() {
        return new CustomStructureContributor(description);
    }

    @Bean
    TestClassContributor testClassContributor(ProjectDescription description, TemplateRenderer templateRenderer) {
        return new TestClassContributor(description, templateRenderer);
    }

    @Bean
    GitIgnoreCustomizer customGitIgnoreCustomizer() {
        return (gitIgnore) -> {
            // IDE 相关
            gitIgnore.getGeneral().add("*.iml");
            gitIgnore.getGeneral().add("*.ipr");
            gitIgnore.getGeneral().add("*.iws");
            gitIgnore.getGeneral().add(".idea/");
            gitIgnore.getGeneral().add(".vscode/");

            // 操作系统相关
            gitIgnore.getGeneral().add(".DS_Store");
            gitIgnore.getGeneral().add("Thumbs.db");

            // 日志文件
            gitIgnore.getGeneral().add("*.log");
            gitIgnore.getGeneral().add("logs/");

            // 临时文件
            gitIgnore.getGeneral().add("*.tmp");
            gitIgnore.getGeneral().add("*.swp");
            gitIgnore.getGeneral().add("*.bak");

            // 环境配置文件
            gitIgnore.getGeneral().add(".env");
            gitIgnore.getGeneral().add(".env.local");

            // Docker 相关
            gitIgnore.getGeneral().add(".docker/");

            // 测试覆盖率报告
            gitIgnore.getGeneral().add("coverage/");
            gitIgnore.getGeneral().add("*.coverage");

            // 压缩包
            gitIgnore.getGeneral().add("*.zip");
            gitIgnore.getGeneral().add("*.tar.gz");
            gitIgnore.getGeneral().add("*.rar");
        };
    }
}
