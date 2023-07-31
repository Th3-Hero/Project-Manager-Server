package com.th3hero.projectmanagerserver.controllers;

import java.util.Collection;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.th3hero.projectmanagerserver.dto.Project;
import com.th3hero.projectmanagerserver.dto.ProjectUpload;
import com.th3hero.projectmanagerserver.services.ProjectService;

import lombok.RequiredArgsConstructor;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/list")
    public Collection<Project> listProjects() {
        return projectService.listProjects();
    }

    @GetMapping("/{projectId}")
    public Project getProjectById(
        @PathVariable UUID projectId
    ) {
        return projectService.getProjectById(projectId);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(
        @RequestBody ProjectUpload project
    ) {
        return projectService.createProject(project);
    }

    @PostMapping("/{projectId}")
    public Project updateProject(
        @PathVariable UUID projectId,
        @RequestBody ProjectUpload project
    ) {
        return projectService.updateProject(projectId, project);
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(
        @PathVariable UUID projectId
    ) {
        projectService.deleteProject(projectId);
    }

}