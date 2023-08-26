package com.th3hero.projectmanagerserver.dto;


import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record Project(
    @NotNull UUID id,
    @NotNull String name,
    @NotNull String description,
    @NotNull List<Field> fields,
    @NotNull List<Tag> tags
) {
}
