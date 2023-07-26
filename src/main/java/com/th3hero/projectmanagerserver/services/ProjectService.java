package com.th3hero.projectmanagerserver.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.objects.Project;
import com.th3hero.projectmanagerserver.repositories.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<ProjectJpa> getAllProjects() {
        return projectRepository.findAll();
    }

    public ProjectJpa getProjectById(UUID projectId) {
        return projectRepository.getReferenceById(projectId);
    }

    public ProjectJpa saveProject(ProjectJpa project) {
        UUID projectId = project.getId();
        ProjectJpa projectEnt = projectRepository.getReferenceById(projectId);
        projectEnt.setName(project.getName());
        projectEnt.setDescription(project.getDescription());

        return projectRepository.save(projectEnt);
    }

    @SuppressWarnings("java:S3655")
    public ProjectJpa createProject(Project projectDTO) {
        String name = projectDTO.getName().isPresent() ? projectDTO.getName().get() : "New Project";
        String description = projectDTO.getDescription().isPresent() ? projectDTO.getDescription().get() : "";
        return projectRepository.save(ProjectJpa.create(name, description));
    }

    public void deleteProject(UUID projectId) {
        projectRepository.deleteById(projectId);
    }

}