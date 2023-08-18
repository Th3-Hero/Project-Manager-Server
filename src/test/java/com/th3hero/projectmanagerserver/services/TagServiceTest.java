package com.th3hero.projectmanagerserver.services;

import com.th3hero.projectmanagerserver.TestEntities;
import com.th3hero.projectmanagerserver.dto.Tag;
import com.th3hero.projectmanagerserver.dto.TagUpload;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.entities.TagJpa;
import com.th3hero.projectmanagerserver.exceptions.ActionAlreadyPreformedException;
import com.th3hero.projectmanagerserver.repositories.ProjectRepository;
import com.th3hero.projectmanagerserver.repositories.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;


    @Test
    void listTags() {
        final var tagJpaList = TestEntities.tagJpas(5);

        when(tagRepository.findAll())
                .thenReturn(tagJpaList);

        final var result = tagService.listTags();

        assertThat(result).extracting(Tag::name).containsExactlyElementsOf(
                tagJpaList.stream().map(TagJpa::getName).toList()
        );
    }

    @Test
    void getTagsOnProject() {
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var projectJpa = TestEntities.projectJpaWithTags(projectId, 4);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));

        final var result = tagService.getTagsOnProject(projectId);

        assertThat(result).hasSize(4);
    }

    @Test
    void getTagsOnProject_missingProject() {
        final var projectId = TestEntities.TEST_PROJECT_ID;

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> tagService.getTagsOnProject(projectId));
    }

    @Test
    void createTag() {
        final var tagUpload = new TagUpload("TestName", "#FFF");

        when(tagRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var result = tagService.createTag(tagUpload);

        assertThat(result.name()).isEqualTo("TestName");
        assertThat(result.hexColor()).isEqualTo("#FFF");
    }

    @Test
    void updateTag() {
        final var tagId = TestEntities.TEST_TAG_ID;
        final var nameUpdated = "UpdatedName";
        final var hex = "#FFF";
        final var updatedTag = new TagUpload(nameUpdated, hex);
        final var tagJpa = TestEntities.tagJpa(tagId, "TestName");

        when(tagRepository.findById(tagId))
                .thenReturn(Optional.of(tagJpa));
        when(tagRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var result = tagService.updateTag(tagId, updatedTag);

        assertThat(result.id()).isEqualTo(tagId);
        assertThat(result.name()).isEqualTo(nameUpdated);
        assertThat(result.hexColor()).isEqualTo(hex);
    }

    @Test
    void updateTag_missingTag() {
        final var tagId = TestEntities.TEST_TAG_ID;
        final var updatedTag = new TagUpload("UpdatedName", "#FFF");

        when(tagRepository.findById(tagId))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> tagService.updateTag(tagId, updatedTag));

        verify(tagRepository, never()).save(any());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2})
    void deleteTagById(Integer projectsWithTagExtraSize) {
        final var tagId = TestEntities.TEST_TAG_ID;
        final var targetTag = TagJpa.builder()
                .id(tagId).name("TargetTag").hexColor("#MSCOT").build();
        final List<ProjectJpa> projectsWithTag = TestEntities.projectJpasWithTag(targetTag, projectsWithTagExtraSize);

        when(tagRepository.existsById(tagId))
                .thenReturn(true);
        when(projectRepository.findAllByTagsId(tagId))
                .thenReturn(projectsWithTag);

        tagService.deleteTagById(tagId);

        verify(tagRepository).existsById(tagId);
        verify(projectRepository).findAllByTagsId(tagId);
        verify(tagRepository).deleteById(tagId);

        for (ProjectJpa project : projectsWithTag) {
            assertThat(project.getTags()).doesNotContain(targetTag);
        }
    }

    @Test
    void deleteTagById_missingTag() {
        final var tagId = TestEntities.TEST_TAG_ID;

        when(tagRepository.existsById(tagId))
                .thenReturn(false);

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> tagService.deleteTagById(tagId));

        verify(projectRepository, never()).findAllByTagsId(any());
        verify(tagRepository, never()).deleteById(any());
    }

    @Test
    void deleteTagById_noProjectsWithTag() {
        final var tagId = TestEntities.TEST_TAG_ID;

        when(tagRepository.existsById(tagId))
                .thenReturn(true);
        when(projectRepository.findAllByTagsId(tagId))
                .thenReturn(List.of());

        tagService.deleteTagById(tagId);

        verify(tagRepository).deleteById(tagId);
    }

    @Test
    void addTagToProject() {
        final var tagId = TestEntities.TEST_TAG_ID;
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var projectJpa = TestEntities.projectJpa(projectId);
        final var tagJpa = TestEntities.tagJpa(tagId, "TestTag");

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));
        when(tagRepository.findById(tagId))
                .thenReturn(Optional.of(tagJpa));
        when(projectRepository.save(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var result = tagService.addTagToProject(tagId, projectId);

        assertThat(result.id()).isEqualTo(projectId);
        assertThat(result.tags()).contains(tagJpa.convertToDto());
    }

    @Test
    void addTagToProject_missingProject() {
        final var tagId = TestEntities.TEST_TAG_ID;
        final var projectId = TestEntities.TEST_PROJECT_ID;

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> tagService.addTagToProject(tagId, projectId));

        verify(tagRepository, never()).findById(any());
        verify(projectRepository, never()).save(any());
    }

    @Test
    void addTagToProject_missingField() {
        final var tagId = TestEntities.TEST_TAG_ID;
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var projectJpa = TestEntities.projectJpa(projectId);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));
        when(tagRepository.findById(tagId))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> tagService.addTagToProject(tagId, projectId));

        verify(projectRepository, never()).save(any());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 4})
    void addTagToProject_tagExistsOnProject(Integer projectsWithTagExtraSize) {
        final var tagId = TestEntities.TEST_TAG_ID;
        final var projectId = TestEntities.TEST_PROJECT_ID;
        final var targetTag = TestEntities.tagJpa(tagId, "TestTag");
        final var projectJpa = TestEntities.projectJpaWithTag(projectId,targetTag, projectsWithTagExtraSize);

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(projectJpa));

        assertThatExceptionOfType(ActionAlreadyPreformedException.class)
                .isThrownBy(() -> tagService.addTagToProject(tagId, projectId));

        verify(tagRepository, never()).findById(any());
        verify(projectRepository, never()).save(any());
    }

}
