package com.th3hero.projectmanagerserver.dto;

import java.util.UUID;

import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;

import jakarta.validation.constraints.NotBlank;

public record Field (
    @NotBlank
    UUID id,
    String title,
    String content
) {
    public FieldJpa convertToJpa (ProjectJpa projectJpa) {
        return FieldJpa.builder()
            .id(this.id())
            .project(projectJpa)
            .title(this.title())
            .content(this.content())
            .build();
    }
}