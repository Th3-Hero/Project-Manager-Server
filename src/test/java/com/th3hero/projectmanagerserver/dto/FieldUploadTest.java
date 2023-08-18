package com.th3hero.projectmanagerserver.dto;

import com.th3hero.projectmanagerserver.TestEntities;
import com.th3hero.projectmanagerserver.entities.FieldJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class FieldUploadTest {

    @Test
    void convertToJpa() {
        final var title = "TestTitle";
        final var content = "TestContent";
        final var fieldUpload = new FieldUpload(title, content);
        final var projectJpa = TestEntities.projectJpa();

        final var result = fieldUpload.convertToJpa(projectJpa);

        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getProject()).isEqualTo(projectJpa);
        assertThat(result).isInstanceOf(FieldJpa.class);
    }

    @Test
    void convertToJpa_nullFields() {
        final var fieldUpload = new FieldUpload(null, null);
        final var projectJpa = TestEntities.projectJpa();

        final var result = fieldUpload.convertToJpa(projectJpa);

        assertThat(result.getTitle()).isEmpty();
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getProject()).isEqualTo(projectJpa);
    }

}
