package com.th3hero.projectmanagerserver.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record Project (
    @NotBlank
    UUID id,

    String name,
    String description,

    List<Field> fields,
    List<Tag> tags
) {}