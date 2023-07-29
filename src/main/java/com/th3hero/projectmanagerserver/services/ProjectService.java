package com.th3hero.projectmanagerserver.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.th3hero.projectmanagerserver.dto.Project;
import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.entities.TagJpa;
import com.th3hero.projectmanagerserver.repositories.ProjectRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final DataManagerFactory dataManagerFactory;

    // public ProjectJpa saveProject(ProjectJpa project) {
    //     UUID projectId = project.getId();
    //     ProjectJpa projectEnt = projectRepository.getReferenceById(projectId);
    //     projectEnt.setName(project.getName());
    //     projectEnt.setDescription(project.getDescription());

    //     return projectRepository.save(projectEnt);
    // }

    // @SuppressWarnings("java:S3655")
    // public ProjectJpa createProject(Project projectDTO) {
    //     String name = projectDTO.getName().isPresent() ? projectDTO.getName().get() : "New Project";
    //     String description = projectDTO.getDescription().isPresent() ? projectDTO.getDescription().get() : "";
    //     return projectRepository.save(ProjectJpa.create(name, description));
    // }

    // public void deleteProject(UUID projectId) {
    //     projectRepository.deleteById(projectId);
    // }

    @SuppressWarnings("java:S1612")
    public List<Project> getAllProjects() {
        List<ProjectJpa> projectsJpa = projectRepository.findAll();

        return projectsJpa.stream().map(project -> dataManagerFactory.convertToDto(project)).toList();
    }

    public Project getProjectById(UUID projectId) {
        Optional<ProjectJpa> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new EntityNotFoundException("Unable to find Project with provided id");
        }
        return dataManagerFactory.convertToDto(project.get());
    }

}