package com.th3hero.projectmanagerserver.dto;


import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record Project(
    @NotNull UUID id,
    @NotNull String name,
    @NotNull String description,
    @NotNull List<Field> fields,
    @NotNull List<Tag> tags
) {
}
