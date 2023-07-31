package com.th3hero.projectmanagerserver.services;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.th3hero.projectmanagerserver.dto.Field;
import com.th3hero.projectmanagerserver.dto.FieldUpload;
import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.exceptions.FailedExpectedEntityRetrievalException;
import com.th3hero.projectmanagerserver.repositories.ProjectRepository;
import com.th3hero.projectmanagerserver.utils.CollectionUtils;

import static com.th3hero.projectmanagerserver.utils.HttpUtil.MISSING_FIELD_WITH_ID;
import static com.th3hero.projectmanagerserver.utils.HttpUtil.MISSING_PROJECT_WITH_ID;



import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FieldService {
    private final ProjectRepository projectRepository;

    @SuppressWarnings("java:S1612")
    public Collection<Field> getFieldsOnProject(UUID projectId) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException(MISSING_PROJECT_WITH_ID));

        return CollectionUtils.transform(
            projectJpa.getFields(),
                FieldJpa::convertToDto
        );
    }

    public Field createField(UUID projectId, FieldUpload fieldUpload) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException(MISSING_PROJECT_WITH_ID));

        FieldJpa fieldJpa = fieldUpload.convertToJpa(projectJpa);

        projectJpa.getFields().add(fieldJpa);
        projectJpa = projectRepository.save(projectJpa);

        List<FieldJpa> updateFields = (List<FieldJpa>) projectJpa.getFields();

        updateFields.removeIf(fieldItem ->
            !(fieldItem.getTitle().equals(fieldJpa.getTitle()) && fieldItem.getContent().equals(fieldJpa.getContent())));

        if (updateFields.isEmpty()) {
            throw new FailedExpectedEntityRetrievalException("I messed up... Small surprise");
        }

        var cretedFieldJpa = updateFields.get(0);
//        if (cretedFieldJpa.getTitle() == null) {
//            cretedFieldJpa.setTitle("");
//        }
//        if (cretedFieldJpa.getContent() == null) {
//            cretedFieldJpa.setTitle("");
//        }

        return cretedFieldJpa.convertToDto();
    }

    public Field updateField(UUID fieldId, UUID projectId, FieldUpload fieldUpload) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException(MISSING_PROJECT_WITH_ID));

        FieldJpa fieldJpa = CollectionUtils
            .findIn(
                projectJpa.getFields(),
                field -> field.getId().equals(fieldId)
            )
            .orElseThrow(() -> new EntityNotFoundException("Failed to find Field on Project with given projectId"));


        if (fieldUpload.title() != null) {
            fieldJpa.setTitle(fieldUpload.title());
        }
        if (fieldUpload.content() != null) {
            fieldJpa.setContent(fieldUpload.content());
        }

        return CollectionUtils.findIn(
                projectRepository.save(projectJpa).getFields(),
                updatedField -> updatedField.getId().equals(fieldId)
            )
            .orElseThrow(() -> new FailedExpectedEntityRetrievalException("Failed to find recently update field"))
            .convertToDto();
    }

    public void deleteField(UUID projectId, UUID fieldId) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException(MISSING_PROJECT_WITH_ID));

        FieldJpa fieldJpa = projectJpa.getFields().stream().filter(field -> field.getId().equals(fieldId)).findFirst()
            .orElseThrow(() -> new EntityNotFoundException(MISSING_FIELD_WITH_ID));

        if (projectJpa.getFields().remove(fieldJpa)) {
            projectRepository.save(projectJpa);
        }
    }

}
