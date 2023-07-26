package com.th3hero.projectmanagerserver.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.objects.Project;
import com.th3hero.projectmanagerserver.services.ProjectService;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/list")
    public List<ProjectJpa> getProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{projectId}")
    public ProjectJpa getProjectById(
        @PathVariable @NotBlank(message = "Project id is required, it cannot be left blank") UUID projectId
    ) {
        return projectService.getProjectById(projectId);
    }

    @PostMapping
    public ProjectJpa saveProject(
        @RequestBody ProjectJpa project
    ) {
        return projectService.saveProject(project);
    }

    @PostMapping("/create")
    public ProjectJpa createProject(
        @RequestBody Project project
    ) {
        return projectService.createProject(project);
    }

    @PostMapping("/delete/{projectId}")
    public String deleteProject(
        @PathVariable @NotBlank(message = "Project id is required to delete a project") UUID projectId
    ) {
        projectService.deleteProject(projectId);
        return "Project deleted";
    }



}