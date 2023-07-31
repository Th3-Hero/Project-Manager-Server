package com.th3hero.projectmanagerserver.services;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.th3hero.projectmanagerserver.dto.TagUpload;
import org.springframework.stereotype.Service;

import com.th3hero.projectmanagerserver.dto.Project;
import com.th3hero.projectmanagerserver.dto.ProjectUpload;
import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.entities.TagJpa;
import com.th3hero.projectmanagerserver.repositories.ProjectRepository;
import com.th3hero.projectmanagerserver.utils.CollectionUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    @SuppressWarnings("java:S1612")
    public Collection<Project> listProjects() {
        return CollectionUtils.transform(
            projectRepository.findAll(),
                ProjectJpa::convertToDto
        );
    }

    public Project getProjectById(UUID projectId) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException("Unable to find project with provided id"));

        return projectJpa.convertToDto();
    }

    public Project createProject(ProjectUpload project) {
        return projectRepository.save(project.convertToJpa()).convertToDto();
    }

    @SuppressWarnings("java:S1612")
    public Project updateProject(UUID projectId, ProjectUpload project) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException("Unable to find existing project with given id"));

        if (project.name() != null) {
            projectJpa.setName(project.name());
        }
        if (project.description() != null) {
            projectJpa.setDescription(projectJpa.getDescription());
        }
        if (project.fields() != null || !project.fields().isEmpty()) {
            List<FieldJpa> fields = project.fields().stream().map(field -> field.convertToJpa(projectJpa)).toList();
            CollectionUtils.replaceList(projectJpa.getFields(), fields);
        }
        if (project.tags() != null || !project.tags().isEmpty()) {
            List<TagJpa> tagJpas = project.tags().stream().map(TagUpload::convertToJpa).toList();
            CollectionUtils.replaceList(projectJpa.getTags(), tagJpas);
        }

        return projectRepository.save(projectJpa).convertToDto();
    }

    public void deleteProject(UUID projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException("Unable to find Project with provided id");
        }

        projectRepository.deleteById(projectId);
    }

}