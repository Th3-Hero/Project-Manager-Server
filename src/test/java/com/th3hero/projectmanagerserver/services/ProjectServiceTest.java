package com.th3hero.projectmanagerserver.services;

import com.th3hero.projectmanagerserver.TestEntities;
import com.th3hero.projectmanagerserver.dto.*;
import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.entities.TagJpa;
import com.th3hero.projectmanagerserver.repositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

;


@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void listProjects() {
        final var projectJpaList = TestEntities.projectJpas(5);

        when(projectRepository.findAll())
                .thenReturn(projectJpaList);

        final var result = projectService.listProjects();

        assertThat(result).extracting(Project::id).containsExactlyElementsOf(
                projectJpaList.stream().map(ProjectJpa::getId).toList()
        );
    }

    @Test
    void getProjectById() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var projectJpa = TestEntities.projectJpa(projectId);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));

        final var result = projectService.getProjectById(projectId);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(projectId);
    }

    @Test
    void getProjectById_missingProject() {
        final var projectId = TestEntities.TEST_PROJECT_ID;

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> projectService.getProjectById(projectId));
    }

    @Test
    void createProject() {
        final var fieldUploads = List.of(
                new FieldUpload("TestTitle1", "TestContent1"),
                new FieldUpload("TestTitle2", "TestContent2")
        );
        final var tagUpload = List.of(new TagUpload("TestName", "#FFF"));
        final var projectUpload = new ProjectUpload(
                "TestName",
                "TestDescription",
                fieldUploads,
                tagUpload
        );

        when(projectRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var result = projectService.createProject(projectUpload);

        assertThat(result.name()).isEqualTo("TestName");
        assertThat(result.description()).isEqualTo("TestDescription");
        assertThat(result.fields()).extracting(Field::title).containsExactlyElementsOf(
                fieldUploads.stream().map(FieldUpload::title).toList()
        );
        assertThat(result.tags()).extracting(Tag::name).containsExactlyElementsOf(
                tagUpload.stream().map(TagUpload::name).toList()
        );
    }

    @Test
    void createProject_nullFields() {
        // Only name is required
        final var projectUpload = new ProjectUpload("TestName", null, null, null);

        when(projectRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var result = projectService.createProject(projectUpload);

        assertThat(result.name()).isEqualTo("TestName");
        assertThat(result.description()).isEmpty();
        assertThat(result.fields()).isEmpty();
        assertThat(result.tags()).isEmpty();
    }

    @Test
    void updateProject() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var fieldUploads = List.of(
                new FieldUpload("UpdatedTitle1", "UpdatedContent1"),
                new FieldUpload("UpdatedTitle2", "UpdatedContent2")
        );
        final var tagUpload = List.of(new TagUpload("UpdatedName", "#FFF"));
        final var projectUpload = new ProjectUpload(
                "UpdatedName",
                "UpdatedDescription",
                fieldUploads,
                tagUpload
        );
        final var projectJpa = TestEntities.projectJpa(projectId);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));
        when(projectRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var result = projectService.updateProject(projectId, projectUpload);

        assertThat(result.id()).isEqualTo(projectId);
        assertThat(result.name()).isEqualTo("UpdatedName");
        assertThat(result.description()).isEqualTo("UpdatedDescription");
        assertThat(result.fields()).extracting(Field::title).containsExactlyElementsOf(
                fieldUploads.stream().map(FieldUpload::title).toList()
        );
        assertThat(result.tags()).extracting(Tag::name).containsExactlyElementsOf(
                tagUpload.stream().map(TagUpload::name).toList()
        );
    }

    @Test
    void updateProject_missingProject() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var projectUpload = new ProjectUpload(
                "UpdatedName",
                "UpdatedDescription",
                List.of(),
                List.of()
        );

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> projectService.updateProject(projectId, projectUpload));
    }

    @Test
    void updateProject_nullFields() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var projectJpa = ProjectJpa.builder()
                .id(projectId)
                .name("TestProject")
                .description("TestDescription").build();
        projectJpa.setFields(TestEntities.fieldJpas(projectJpa, 3));
        projectJpa.setTags(TestEntities.tagJpas(2));
        final var projectUpload = new ProjectUpload("UpdatedName", null, null, null);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));
        when(projectRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var result = projectService.updateProject(projectId, projectUpload);

        assertThat(result.name()).isEqualTo("UpdatedName");
        assertThat(result.description()).isEqualTo("TestDescription");
        assertThat(result.fields()).extracting(Field::title).containsExactlyElementsOf(
                projectJpa.getFields().stream().map(FieldJpa::getTitle).toList()
        );
        assertThat(result.tags()).extracting(Tag::name).containsExactlyElementsOf(
                projectJpa.getTags().stream().map(TagJpa::getName).toList()
        );
    }

    @Test
    void deleteProject() {
        final var projectId = TestEntities.TEST_PROJECT_ID;

        when(projectRepository.existsById(projectId))
                .thenReturn(true);

        projectService.deleteProject(projectId);

        verify(projectRepository).deleteById(projectId);
    }

    @Test
    void deleteProject_missingProject() {
        final var projectId = TestEntities.TEST_PROJECT_ID;

        when(projectRepository.existsById(projectId))
                .thenReturn(false);

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> projectService.deleteProject(projectId));
        verify(projectRepository, never()).deleteById(projectId);
    }
}
