package com.th3hero.projectmanagerserver.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record Field(
    @NotNull UUID id,
    @NotNull String title,
    @NotNull String content
) { }
