package dev.meirong.showcase.initializr_demo.customizations;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** A {@link ProjectContributor} that runs {@code git init} on the project root. */
public class GitInitProjectContributor implements ProjectContributor {

    private static final Logger log = LoggerFactory.getLogger(GitInitProjectContributor.class);

    @Override
    public void contribute(Path projectRoot) throws IOException {
        log.info("Initializing Git repository in {}", projectRoot);
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("git", "init");
            processBuilder.directory(projectRoot.toFile());
            processBuilder.redirectErrorStream(true); // Merge error and output streams

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                // Note: The process output is not captured here, but will appear in the main
                // application logs.
                throw new IOException("Failed to run 'git init', exit code: " + exitCode);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while waiting for 'git init' to complete", ex);
        }
    }
}
