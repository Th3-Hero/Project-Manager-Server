package com.th3hero.projectmanagerserver.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record Field(
    @NotNull UUID id,
    @NotNull String title,
    @NotNull String content
) { }
