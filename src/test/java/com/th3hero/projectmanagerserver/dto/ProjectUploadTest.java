package com.th3hero.projectmanagerserver.dto;

import com.th3hero.projectmanagerserver.TestEntities;
import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static  org.mockito.Mockito.when;
import static  org.mockito.Mockito.never;
import static  org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProjectUploadTest {

    @Test
    void customGetters() {
        final var name = "TestName";
        final var description = "TestDescription";
        final var fields = List.of(
                new FieldUpload("Title", "Content"),
                new FieldUpload("Title2", "Content2")
        );
        final var tags = List.of(
                new TagUpload("Name", "#FFF"),
                new TagUpload("Name2", "#MSCT")
        );
        final var projectUpload = new ProjectUpload(name, description, fields, tags);

        assertThat(projectUpload.description()).isEqualTo(description);
        assertThat(projectUpload.fields()).isEqualTo(fields);
        assertThat(projectUpload.tags()).isEqualTo(tags);
    }

    @Test
    void customGetters_nullFields() {
//      Name is required, the controller will reject the request if it's blank
        final var name = "TestName";
        final var projectUpload = new ProjectUpload(name, null, null, null);

        assertThat(projectUpload.description()).isNotNull();
        assertThat(projectUpload.fields()).isNotNull();
        assertThat(projectUpload.tags()).isNotNull();
    }

    @Test
    void convertToJpa() {
        final var name = "TestName";
        final var description = "TestDescription";
        final var fields = List.of(
                new FieldUpload("Title", "Content"),
                new FieldUpload("Title2", "Content2")
        );
        final var tags = List.of(
                new TagUpload("Name", "#FFF"),
                new TagUpload("Name2", "#MSCT")
        );
        final var projectUpload = new ProjectUpload(name, description, fields, tags);

        final var result = projectUpload.convertToJpa();

        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getDescription()).isEqualTo(description);
        assertThat(result.getFields()).hasSize(fields.size());
        assertThat(result.getTags()).hasSize(tags.size());
        assertThat(result).isInstanceOf(ProjectJpa.class);
    }

    @Test
    void convertToJpa_nullFields() {
        final var name = "TestName";
        final var projectUpload = new ProjectUpload(name, null, null, null);

        final var result = projectUpload.convertToJpa();

        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getDescription()).isNotNull();
        assertThat(result.getFields()).isNotNull();
        assertThat(result.getTags()).isNotNull();
        assertThat(result).isInstanceOf(ProjectJpa.class);
    }
}
