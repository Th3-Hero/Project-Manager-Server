package com.th3hero.projectmanagerserver.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.th3hero.projectmanagerserver.dto.Field;
import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.repositories.ProjectRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FieldService {
    private final ProjectRepository projectRepository;

    @SuppressWarnings("java:S1612")
    public List<Field> getFieldsOnProject(UUID projectId) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException("Unable to find project with provided id"));

        return projectJpa.getFields()
            .stream()
            .map(field -> field.convertToDto())
            .toList();
    }

    public void deleteField(UUID projectId, UUID fieldId) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException("Unable to find project with provided id"));

        FieldJpa fieldJpa = projectJpa.getFields().stream().filter(field -> field.getId().equals(fieldId)).findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Unable to find field with provided id"));

        if (projectJpa.getFields().remove(fieldJpa)) {
            projectRepository.save(projectJpa);
        }

    }

    // public Field updateField(Field field) {
    //     // TODO: implement
    // }

}
