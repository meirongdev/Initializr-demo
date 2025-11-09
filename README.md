## Introduction

This is a demo project for showcasing the capabilities of the Initializr tool.

## Prerequisites
- Java25 or higher
- Maven 3.9.10 or higher

## Usage

start the Initializr service locally:

```bash
./mvnw spring-boot:run
```

Generate the project with specific artifactId

```bash
http http://localhost:8080/starter.zip artifactId==new-app -d;
```

## Demo

Demo of generating a project with custom structure and configurations:

![Initializr Demo](docs/demo.gif)

## TODO

- [ ] Generate sample code files in the custom structure
- [ ] Add Makefile for build automation
- [ ] Add git hooks for pre-commit checks
- [ ] Generate ci files for popular CI/CD platforms
