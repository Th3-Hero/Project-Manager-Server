package com.th3hero.projectmanagerserver.services;

import com.th3hero.projectmanagerserver.TestEntities;
import com.th3hero.projectmanagerserver.dto.Field;
import com.th3hero.projectmanagerserver.dto.FieldUpload;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.repositories.ProjectRepository;
import com.th3hero.projectmanagerserver.utils.HttpUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FieldServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private FieldService fieldService;

    @Test
    void getFieldsOnProject() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var numOfFields = 3;
        final var projectJpa = TestEntities.projectJpaWithFields(projectId, numOfFields);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));

        final var fieldsOnProject = fieldService.getFieldsOnProject(projectId);

        assertThat(fieldsOnProject).hasSize(numOfFields);
    }

    @Test
    void getFieldsOnProject_missingProject() {
        final var projectId = TestEntities.TEST_PROJECT_ID;

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> fieldService.getFieldsOnProject(projectId));
    }

    @Test
    void createField() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var projectJpa = TestEntities.projectJpa(projectId);
        final var fieldUpload = new FieldUpload("TestTitle", "TestContent");

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));
        when(projectRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final Field result = fieldService.createField(projectId, fieldUpload);

        assertThat(result.title())
                .isEqualTo(fieldUpload.title());
        assertThat(result.content())
                .isEqualTo(fieldUpload.content());
    }

    @Test
    void createField_missingProject() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var fieldUpload = new FieldUpload("TestTitle", "TestContent");

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> fieldService.createField(projectId, fieldUpload));
    }

    @Test
    void createField_nullObjectFields() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var projectJpa = TestEntities.projectJpa(projectId);
        final var fieldUpload = new FieldUpload(null, null);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));
        when(projectRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final Field result = fieldService.createField(projectId, fieldUpload);

        assertThat(result.title()).isNotNull();
        assertThat(result.content()).isNotNull();
    }

    @Test
    void updateField() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var fieldId = TestEntities.TEST_FIELD_ID;
        final var projectJpa = TestEntities.projectJpaWithFields(projectId, 4);
        final var fieldJpa = TestEntities.fieldJpa(fieldId, "TestField", projectJpa);
        final var fieldUpload = new FieldUpload("UpdatedTitle", "UpdatedContent");
        projectJpa.getFields().add(fieldJpa);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));
        when(projectRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var result = fieldService.updateField(fieldId, projectId, fieldUpload);

        assertThat(result.id()).isEqualTo(fieldId);
        assertThat(result.title()).isEqualTo("UpdatedTitle");
        assertThat(result.content()).isEqualTo("UpdatedContent");
    }

    @Test
    void updateField_missingProject() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var fieldId = TestEntities.TEST_FIELD_ID;
        final var fieldUpload = new FieldUpload("UpdatedTitle", "UpdatedContent");

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> fieldService.updateField(fieldId, projectId, fieldUpload));
    }

    @Test
    void updateField_missingField() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var fieldId = TestEntities.TEST_FIELD_ID;
        final var projectJpa = TestEntities.projectJpaWithFields(projectId, 4);
        final var fieldUpload = new FieldUpload("UpdatedTitle", "UpdatedContent");

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> fieldService.updateField(fieldId, projectId, fieldUpload));
    }

    @Test
    void updateField_nullObjectFields() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var fieldId = TestEntities.TEST_FIELD_ID;
        final var projectJpa = TestEntities.projectJpaWithFields(projectId, 4);
        final var fieldJpa = TestEntities.fieldJpa(fieldId, "TestField", projectJpa);
        final var fieldUpload = new FieldUpload(null, null);
        projectJpa.getFields().add(fieldJpa);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));
        when(projectRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var result = fieldService.updateField(fieldId, projectId, fieldUpload);

        assertThat(result.title()).isNotNull();
        assertThat(result.content()).isNotNull();
    }

    @Test
    void deleteField() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var fieldId = TestEntities.TEST_FIELD_ID;
        final var projectJpa = TestEntities.projectJpaWithFields(projectId, 4);
        final var fieldJpa = TestEntities.fieldJpa(fieldId, "TestField", projectJpa);
        projectJpa.getFields().add(fieldJpa);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));
        when(projectRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        ArgumentCaptor<ProjectJpa> projectJpaCaptor = ArgumentCaptor.forClass(ProjectJpa.class);

        fieldService.deleteField(projectId, fieldId);

        verify(projectRepository).save(projectJpaCaptor.capture());
        assertThat(projectJpaCaptor.getValue().getFields()).hasSize(4);
    }

    @Test
    void deleteProject_missingProject() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var fieldId = TestEntities.TEST_FIELD_ID;

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> fieldService.deleteField(projectId, fieldId))
                .withMessage(HttpUtil.MISSING_PROJECT_WITH_ID);

        verify(projectRepository, never()).save(any());
    }

    @Test
    void deleteProject_missingField() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var fieldId = TestEntities.TEST_FIELD_ID;
        final var projectJpa = TestEntities.projectJpaWithFields(projectId, 4);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> fieldService.deleteField(projectId, fieldId))
                .withMessage(HttpUtil.MISSING_FIELD_WITH_ID);

        verify(projectRepository, never()).save(any());
    }
}
