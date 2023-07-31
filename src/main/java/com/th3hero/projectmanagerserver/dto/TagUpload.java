package com.th3hero.projectmanagerserver.dto;

import com.th3hero.projectmanagerserver.entities.TagJpa;

import jakarta.validation.constraints.NotBlank;

public record TagUpload(
    @NotBlank(message = "Tag name is required")
    String name,
    @NotBlank(message = "Tag color is required")
    String hexColor
) {
    public TagJpa convertToJpa() {
        return TagJpa.builder()
            .name(this.name())
            .hexColor(this.hexColor())
            .build();
    }
}