package com.th3hero.projectmanagerserver.dto;

import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;

public record FieldUpload(
    String title,
    String content
) {
    public String title() {
        return (this.title == null) ? "" : this.title;
    }

    public String content() {
        return (this.content == null) ? "" : this.content;
    }

    public FieldJpa convertToJpa(ProjectJpa projectJpa) {
        return FieldJpa.builder()
            .project(projectJpa)
            .title(this.title())
            .content(this.content())
            .build();
    }
}