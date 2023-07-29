package com.th3hero.projectmanagerserver.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.th3hero.projectmanagerserver.dto.Tag;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.entities.TagJpa;
import com.th3hero.projectmanagerserver.repositories.ProjectRepository;
import com.th3hero.projectmanagerserver.repositories.TagRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;

    @SuppressWarnings("java:S1612")
    public List<Tag> getTagsOnProject(UUID projectId) {
        ProjectJpa projectJpa = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException("Unable to find project with provided id"));

        return projectJpa.getTags()
            .stream()
            .map(tag -> tag.convertToDto())
            .toList();
    }

    public void deleteTagById(UUID tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new EntityNotFoundException("Unable to find Tag with provided id");
        }

        // Search for all projects that use the given tag
        List<ProjectJpa> projectJpasToUpdate = projectRepository.findAll()
            .stream()
            .filter(project -> project.getTags()
                .stream()
                .anyMatch(tag -> tag.getId().equals(tagId)))
            .toList();

        // For all projects that use the given tag
        projectJpasToUpdate.forEach(project ->
            // Get tags, remove the tag with the matching id
            project.getTags().remove(
                project.getTags()
                    .stream()
                    .filter(tag -> tag.getId().equals(tagId))
                    .findFirst()
                    .get()
            )
        );

        tagRepository.deleteById(tagId);
    }

    public Tag updateTag(Tag tag) {
        TagJpa tagJpa = tagRepository.findById(tag.id())
            .orElseThrow(() -> new EntityNotFoundException("Unable to find existing tag with given id"));

        tagJpa.setName(tag.name());
        tagJpa.setHexColor(tag.hexColor());

        return tagRepository.save(tagJpa).convertToDto();
    }

}