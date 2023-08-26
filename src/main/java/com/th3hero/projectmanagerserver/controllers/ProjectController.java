package com.th3hero.projectmanagerserver.controllers;

import com.th3hero.projectmanagerserver.dto.Project;
import com.th3hero.projectmanagerserver.dto.ProjectUpload;
import com.th3hero.projectmanagerserver.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Tag(name = "Project Controller", description = "Handles all operations regarding Projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/list")
    @Operation(summary = "Get a list of all projects")
    public Collection<Project> listProjects() {
        return projectService.listProjects();
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "Get a project by its id")
    public Project getProjectById(
        @PathVariable @NotBlank(message = "Project id is required") UUID projectId
    ) {
        return projectService.getProjectById(projectId);
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new Project")
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(
        @RequestBody ProjectUpload project
    ) {
        return projectService.createProject(project);
    }

    @PostMapping("/{projectId}")
    @Operation(summary = "Update an existing Project")
    public Project updateProject(
        @PathVariable @NotBlank(message = "Project id is required") UUID projectId,
        @RequestBody ProjectUpload project
    ) {
        return projectService.updateProject(projectId, project);
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "Delete a Project by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(
        @PathVariable UUID projectId
    ) {
        projectService.deleteProject(projectId);
    }

}