package dev.meirong.showcase.initializr_demo;

import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
// @EnableCaching
@ComponentScan(
        basePackages = "dev.meirong.showcase.initializr_demo",
        excludeFilters =
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        classes = ProjectGenerationConfiguration.class))
public class InitializrDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(InitializrDemoApplication.class, args);
    }
}
