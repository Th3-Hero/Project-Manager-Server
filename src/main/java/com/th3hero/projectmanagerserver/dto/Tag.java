package com.th3hero.projectmanagerserver.dto;

import java.util.UUID;

import com.th3hero.projectmanagerserver.entities.TagJpa;

import jakarta.validation.constraints.NotBlank;

public record Tag (
    @NotBlank
    UUID id,
    @NotBlank
    String name,
    String hexColor
) {
    public TagJpa convertToJpa() {
        return TagJpa.builder()
            .id(this.id())
            .name(this.name())
            .hexColor(this.hexColor())
            .build();
    }
}