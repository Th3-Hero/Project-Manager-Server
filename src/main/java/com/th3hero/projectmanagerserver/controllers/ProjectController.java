package com.th3hero.projectmanagerserver.controllers;

import java.util.ArrayList;
import java.util.List;
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
import com.th3hero.projectmanagerserver.services.ProjectService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/list")
    public List<Project> getProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{projectId}")
    public Project getProjectById(
        @PathVariable UUID projectId
    ) {
        return projectService.getProjectById(projectId);
    }

    // TODO: I'm lost, I want name to be required and to be able to only provide name
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(
        @RequestBody Project project
    ) {
        return projectService.createProject(project);
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(
        @PathVariable UUID projectId
    ) {
        projectService.deleteProject(projectId);
    }

    @PostMapping("/")
    public Project updateProject(
        @RequestBody Project project
    ) {
        return projectService.updateProject(project);
    }

}