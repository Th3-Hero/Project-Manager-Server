package com.th3hero.projectmanagerserver.services;

import com.th3hero.projectmanagerserver.dto.Project;
import com.th3hero.projectmanagerserver.dto.Tag;
import com.th3hero.projectmanagerserver.dto.TagUpload;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.entities.TagJpa;
import com.th3hero.projectmanagerserver.exceptions.ActionAlreadyPreformedException;
import com.th3hero.projectmanagerserver.exceptions.FailedExpectedEntityRetrievalException;
import com.th3hero.projectmanagerserver.repositories.ProjectRepository;
import com.th3hero.projectmanagerserver.repositories.TagRepository;
import com.th3hero.projectmanagerserver.utils.CollectionUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.th3hero.projectmanagerserver.utils.HttpErrorUtil.MISSING_PROJECT_WITH_ID;
import static com.th3hero.projectmanagerserver.utils.HttpErrorUtil.MISSING_TAG_WITH_ID;


@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;

    public Collection<Tag> listTags() {
        return CollectionUtils.transform(
            tagRepository.findAll(),
                TagJpa::convertToDto
        );
    }

    public Collection<Tag> getTagsOnProject(UUID projectId) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException(MISSING_PROJECT_WITH_ID));

        return CollectionUtils.transform(
            projectJpa.getTags(),
                TagJpa::convertToDto
        );
    }

    public Tag createTag(TagUpload tagUpload) {
        TagJpa tagJpa = TagJpa.builder()
            .name(tagUpload.name())
            .hexColor(tagUpload.hexColor())
            .build();

        return tagRepository.save(tagJpa).convertToDto();
    }

    public Tag updateTag(UUID tagId, TagUpload tagUpload) {
        TagJpa tagJpa = tagRepository.findById(tagId)
            .orElseThrow(() -> new EntityNotFoundException(MISSING_TAG_WITH_ID));

        tagJpa.setName(tagUpload.name());
        tagJpa.setHexColor(tagUpload.hexColor());

        return tagRepository.save(tagJpa).convertToDto();
    }

    public void deleteTagById(UUID tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new EntityNotFoundException(MISSING_TAG_WITH_ID);
        }

        List<ProjectJpa> projectJpasToUpdate = projectRepository.findAllByTagsId(tagId);


        // For all projects that use the given tag
        projectJpasToUpdate.forEach(project ->
            // Get tags, remove the tag with the matching id
            project.getTags().remove(
                CollectionUtils.findIn(
                    project.getTags(),
                    tag -> tag.getId().equals(tagId)
                ).orElseThrow(() -> new FailedExpectedEntityRetrievalException("Failed to find expected entity to remove tag from"))
            )
        );

        tagRepository.deleteById(tagId);
    }

    public Project addTagToProject(UUID tagId, UUID projectId) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException(MISSING_PROJECT_WITH_ID));

        if (projectJpa.getTags().stream().anyMatch(tag -> tag.getId().equals(tagId))) {
            throw new ActionAlreadyPreformedException("The tag was already found on the specified project");
        }

        TagJpa tagJpa = tagRepository.findById(tagId)
            .orElseThrow(() -> new EntityNotFoundException(MISSING_TAG_WITH_ID));

        projectJpa.getTags().add(tagJpa);

        return projectRepository.save(projectJpa).convertToDto();
    }

}