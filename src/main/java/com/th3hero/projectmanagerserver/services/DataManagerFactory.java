package com.th3hero.projectmanagerserver.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.th3hero.projectmanagerserver.dto.Field;
import com.th3hero.projectmanagerserver.dto.Project;
import com.th3hero.projectmanagerserver.dto.Tag;
import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.entities.TagJpa;
import com.th3hero.projectmanagerserver.utils.ListUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataManagerFactory {

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

    public Field convertToDto (FieldJpa fieldJpa) {
        return new Field(
            fieldJpa.getId(),
            fieldJpa.getTitle(),
            fieldJpa.getContent()
        );
    }

    public FieldJpa convertToJpa (Field field, ProjectJpa projectJpa) {
        return FieldJpa.builder()
            .id(field.id())
            .project(projectJpa)
            .title(field.title())
            .content(field.content())
            .build();
    }

    public Project convertToDto(ProjectJpa projectJpa) {
        return new Project(
            projectJpa.getId(),
            projectJpa.getName(),
            projectJpa.getDescription(),
            projectJpa.getFields().stream().map(this::convertToDto).toList(),
            projectJpa.getTags().stream().map(this::convertToDto).toList()
        );
    }

    public ProjectJpa convertToJpa(Project project) {
        ProjectJpa projectJpa = ProjectJpa.builder()
            .id(project.id())
            .name(project.name())
            .description(project.description())
            .build();

        List<FieldJpa> fields = project.fields().stream().map(field -> convertToJpa(field, projectJpa)).toList();
        ListUtils.replaceList(projectJpa.getFields(), fields);
        List<TagJpa> tags = project.tags().stream().map(this::convertToJpa).toList();
        ListUtils.replaceList(projectJpa.getTags(), tags);
        return projectJpa;
    }
}