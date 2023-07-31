package com.th3hero.projectmanagerserver.dto;

import java.util.List;

import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.entities.TagJpa;
import com.th3hero.projectmanagerserver.utils.CollectionUtils;

import jakarta.validation.constraints.NotBlank;

@SuppressWarnings("java:S1612")
public record ProjectUpload(
    @NotBlank(message = "Project name is required")
    String name,
    String description,
    List<FieldUpload> fields,
    List<TagUpload> tags
) {
    public String description() {
        return (this.description == null) ? "" : this.description;
    }

    public List<FieldUpload> fields() {
        return (this.fields == null) ? List.of() : this.fields;
    }

    public List<TagUpload> tags() {
        return (this.tags == null) ? List.of() : this.tags;
    }

    public ProjectJpa convertToJpa() {
        ProjectJpa projectJpa = ProjectJpa.builder()
            .name(this.name())
            .description(this.description())
            .build();

        List<FieldJpa> fieldJpas = this.fields().stream().map(field -> field.convertToJpa(projectJpa)).toList();
        CollectionUtils.replaceList(projectJpa.getFields(), fieldJpas);

        List<TagJpa> tagJpas = this.tags().stream().map(TagUpload::convertToJpa).toList();
        CollectionUtils.replaceList(projectJpa.getTags(), tagJpas);

        return projectJpa;
    }
}