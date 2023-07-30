package com.th3hero.projectmanagerserver.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.context.properties.bind.DefaultValue;

import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.entities.TagJpa;
import com.th3hero.projectmanagerserver.utils.CollectionUtils;

import jakarta.validation.constraints.NotBlank;


@SuppressWarnings("java:S1612")
public record Project (
    UUID id,

    @NotBlank(message = "Project name is required")
    String name,
    String description,

    List<Field> fields,
    List<Tag> tags
) {
    private void replaceFields(ProjectJpa projectJpa) {
        List<FieldJpa> fields = this.fields().stream().map(field -> field.convertToJpa(projectJpa)).toList();
        CollectionUtils.replaceList(projectJpa.getFields(), fields);
    }

    private void replaceTags(ProjectJpa projectJpa) {
        List<TagJpa> tags = this.tags().stream().map(tag -> tag.convertToJpa()).toList();
        CollectionUtils.replaceList(projectJpa.getTags(), tags);
    }

    public ProjectJpa convertToJpa() {
        ProjectJpa projectJpa = ProjectJpa.builder()
            .id(this.id())
            .name(this.name())
            .description(this.description())
            .build();

        this.replaceFields(projectJpa);
        this.replaceTags(projectJpa);

        return projectJpa;
    }

    public ProjectJpa convertToJpa(boolean newEntry) {
        if (!newEntry) {
            return this.convertToJpa();
        }

        ProjectJpa projectJpa = ProjectJpa.builder()
            .name(this.name())
            .description(this.description())
            .build();

        this.replaceFields(projectJpa);
        this.replaceTags(projectJpa);

        return projectJpa;
    }
}