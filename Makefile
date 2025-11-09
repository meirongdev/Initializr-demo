.PHONY: help build test clean format format-check install-hooks pre-commit-hook run

MAVEN := ./mvnw

help:
	@echo "Available targets:"
	@echo "  make build              - Build the project"
	@echo "  make test               - Run tests"
	@echo "  make clean              - Clean build artifacts"
	@echo "  make format             - Format code with Spotless"
	@echo "  make format-check       - Check code format without applying changes"
	@echo "  make install-hooks      - Install Git pre-commit hook"
	@echo "  make remove-hooks       - Remove Git pre-commit hook"
	@echo "  make run                - Run the application"
	@echo "  make help               - Show this help message"

build:
	$(MAVEN) clean package

test:
	$(MAVEN) test

clean:
	$(MAVEN) clean

# Format code using Spotless (automatically applies changes)
format:
	@echo "Formatting code with Spotless..."
	$(MAVEN) spotless:apply
	@echo "✓ Code formatted successfully"

# Check code format (does NOT apply changes)
format-check:
	@echo "Checking code format with Spotless..."
	$(MAVEN) spotless:check
	@echo "✓ Code format check passed"

# Install Git pre-commit hook
install-hooks:
	@echo "Installing Git pre-commit hook..."
	@mkdir -p .git/hooks
	@echo '#!/bin/bash' > .git/hooks/pre-commit
	@echo 'set -e' >> .git/hooks/pre-commit
	@echo '' >> .git/hooks/pre-commit
	@echo 'echo "Running Spotless format check before commit..."' >> .git/hooks/pre-commit
    @echo 'if ! ./mvnw spotless:check > /dev/null 2>&1; then' >> .git/hooks/pre-commit
	@echo '    echo "❌ Code format check failed!"' >> .git/hooks/pre-commit
	@echo '    echo "Run: make format"' >> .git/hooks/pre-commit
	@echo '    exit 1' >> .git/hooks/pre-commit
	@echo 'fi' >> .git/hooks/pre-commit
	@echo 'echo "✓ Code format check passed"' >> .git/hooks/pre-commit
	@chmod +x .git/hooks/pre-commit
	@echo "✓ Git pre-commit hook installed successfully"

# Remove Git pre-commit hook
remove-hooks:
	@echo "Removing Git pre-commit hook..."
	@rm -f .git/hooks/pre-commit
	@echo "✓ Git pre-commit hook removed"

# Run the application
run:
	$(MAVEN) spring-boot:run