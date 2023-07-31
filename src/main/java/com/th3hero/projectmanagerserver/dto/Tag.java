package com.th3hero.projectmanagerserver.dto;

import java.util.UUID;


import jakarta.validation.constraints.NotNull;

public record Tag(
    @NotNull UUID id,
    @NotNull String name,
    @NotNull String hexColor
) { }