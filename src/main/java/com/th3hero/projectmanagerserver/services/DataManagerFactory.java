package com.th3hero.projectmanagerserver.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.th3hero.projectmanagerserver.dto.Tag;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.entities.TagJpa;
import com.th3hero.projectmanagerserver.objects.Project;
import com.th3hero.projectmanagerserver.repositories.ProjectRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DataManagerFactory {
    private final ProjectRepository projectRepository;

    public Tag convertToDto(TagJpa tagJpa) {
        return new Tag(
            tagJpa.getId(),
            tagJpa.getName(),
            tagJpa.getHexColor()
        );
    }

    public TagJpa convertToJpa(Tag tag) {
        return TagJpa.builder()
            .id(tag.id())
            .name(tag.name())
            .hexColor(tag.hexColor())
            .build();
    }

    // public Project convertToDto(ProjectJpa projectJpa) {
    //     return new Project(
    //         projectJpa.getId(),
    //         projectJpa.getName(),
    //         projectJpa.getDescription(),
    //         projectJpa.getFields().stream().map(this::convertToDto).toList(),
    //         projectJpa.getTags().stream().map(this::convertToDto).toList()
    //     );
    // }

    public ProjectJpa convertToJpa(Project project) {
        if (project.getId() == null) {
            return projectRepository.save(
                ProjectJpa.builder()
                    .name(project.getName())
                    .description(project.getDescription())
                    .fields(project.getFields().stream().map(this::convertToJpa).toList()))
                    .tags(project.getTags().stream().map(this::convertToJpa).toList())
                    .build()
            );
        }

        Optional<ProjectJpa> projectJpa = projectRepository.findById(project.getId());
        if (projectJpa.isEmpty()) {
            throw new EntityNotFoundException("Unable to find Project with provided id");
        }

        return ProjectJpa.builder()
        .id(project.getId())
        .name(project.getName())
        .build();
    }



}