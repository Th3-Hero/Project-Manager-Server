package com.th3hero.projectmanagerserver.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record Field (
    @NotBlank
    UUID id,
    String title,
    String content
) {}