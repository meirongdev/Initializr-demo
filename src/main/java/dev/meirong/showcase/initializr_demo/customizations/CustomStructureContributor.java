package dev.meirong.showcase.initializr_demo.customizations;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CustomStructureContributor implements ProjectContributor {

    private final ProjectDescription description;

    public CustomStructureContributor(ProjectDescription description) {
        this.description = description;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        Path basePackage = projectRoot
                .resolve("src/main/java")
                .resolve(description.getPackageName().replace('.', '/'));
        createLayeredStructure(basePackage);

        Path resourcesRoot = projectRoot.resolve("src/main/resources");
        createLiquibaseStructure(resourcesRoot);

        Path testResourcesRoot = projectRoot.resolve("src/test/resources");
        createTestResourcesStructure(testResourcesRoot);
    }

    private void createLayeredStructure(Path basePackage) throws IOException {
        List<String> layers = List.of(
                "controller",
                "service",
                "service/impl",
                "repository",
                "model",
                "model/entity",
                "model/dto",
                "model/vo",
                "config",
                "common",
                "common/exception",
                "common/util"
        );
        for (String layer : layers) {
            Files.createDirectories(basePackage.resolve(layer));
        }
    }

    private void createLiquibaseStructure(Path resourcesRoot) throws IOException {
        Path dbRoot = resourcesRoot.resolve("db/changelog");
        Path changesDir = dbRoot.resolve("changes");
        Files.createDirectories(changesDir);

        // master
        Path master = dbRoot.resolve("db.changelog-master.yaml");
        if (!Files.exists(master)) {
            write(master, """
                    databaseChangeLog:
                      - includeAll:
                          path: db/changelog/changes
                    """);
        }

        // 示例首个变更文件（可删除）
        Path firstChange = changesDir.resolve("0001-initial-schema.yaml");
        if (!Files.exists(firstChange)) {
            write(firstChange, """
                    databaseChangeLog:
                      - changeSet:
                          id: initial-schema
                          author: auto
                          changes:
                            - createTable:
                                tableName: sample_table
                                columns:
                                  - column:
                                      name: id
                                      type: BIGINT
                                      constraints:
                                        primaryKey: true
                                        nullable: false
                                  - column:
                                      name: name
                                      type: VARCHAR(255)
                                      constraints:
                                        nullable: false
                    """);
        }

        // liquibase.properties (MySQL)
        Path props = resourcesRoot.resolve("liquibase.properties");
        if (!Files.exists(props)) {
            write(props, """
                    changeLogFile=db/changelog/db.changelog-master.yaml
                    url=jdbc:mysql://localhost:3306/demo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
                    username=demo
                    password=demo
                    driver=com.mysql.cj.jdbc.Driver
                    """);
        }
    }

    private void createTestResourcesStructure(Path testResourcesRoot) throws IOException {
        // 创建基本的测试资源目录
        Files.createDirectories(testResourcesRoot);

        // 可选：创建测试用的 application-test.yaml
        Path testConfig = testResourcesRoot.resolve("application-test.yaml");
        if (!Files.exists(testConfig)) {
            write(testConfig, """
                spring:
                  autoconfigure:
                    exclude:
                      - org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
                      - org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
                  liquibase:
                    enabled: false
                    """);
        }
    }

    private void write(Path file, String content) throws IOException {
        Files.createDirectories(file.getParent());
        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(file))) {
            out.print(content);
        }
    }
}
