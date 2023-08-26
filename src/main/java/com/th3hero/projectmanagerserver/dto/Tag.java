package com.th3hero.projectmanagerserver.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record Tag(
    @NotNull UUID id,
    @NotNull String name,
    @NotNull String hexColor
) { }