package com.th3hero.projectmanagerserver.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.th3hero.projectmanagerserver.dto.Project;
import com.th3hero.projectmanagerserver.dto.ProjectUpload;
import com.th3hero.projectmanagerserver.dto.TagUpload;
import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.entities.TagJpa;
import com.th3hero.projectmanagerserver.repositories.ProjectRepository;
import com.th3hero.projectmanagerserver.utils.CollectionUtils;
import com.th3hero.projectmanagerserver.utils.HttpErrorUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Collection<Project> listProjects() {
        return CollectionUtils.transform(
            projectRepository.findAll(),
                ProjectJpa::convertToDto
        );
    }

    public Project getProjectById(UUID projectId) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException(HttpErrorUtil.MISSING_PROJECT_WITH_ID));

        return projectJpa.convertToDto();
    }

    public Project createProject(ProjectUpload project) {
        return projectRepository.save(project.convertToJpa()).convertToDto();
    }

    public Project updateProject(UUID projectId, ProjectUpload projectUpload) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException(HttpErrorUtil.MISSING_PROJECT_WITH_ID));

        if (projectUpload.name() != null) {
            projectJpa.setName(projectUpload.name());
        }
        if (projectUpload.description() != null && !projectUpload.description().isEmpty()) {
            projectJpa.setDescription(projectUpload.description());
        }
        if (projectUpload.fields() != null && !projectUpload.fields().isEmpty()) {
            List<FieldJpa> fields = projectUpload.fields().stream().map(field -> field.convertToJpa(projectJpa)).toList();
            CollectionUtils.replaceList(projectJpa.getFields(), fields);
        }
        if (projectUpload.tags() != null && !projectUpload.tags().isEmpty()) {
            List<TagJpa> tagJpas = projectUpload.tags().stream().map(TagUpload::convertToJpa).toList();
            CollectionUtils.replaceList(projectJpa.getTags(), tagJpas);
        }

        return projectRepository.save(projectJpa).convertToDto();
    }

    public void deleteProject(UUID projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException(HttpErrorUtil.MISSING_PROJECT_WITH_ID);
        }

        projectRepository.deleteById(projectId);
    }

    public byte[] exportProjectToYaml(UUID projectId) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(HttpErrorUtil.MISSING_PROJECT_WITH_ID));

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            return mapper.writeValueAsBytes(projectJpa.convertToDto());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}