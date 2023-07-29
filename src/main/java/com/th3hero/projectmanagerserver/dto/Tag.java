package com.th3hero.projectmanagerserver.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record Tag (
    @NotBlank
    UUID id,
    String name,
    String hexColor
) {}