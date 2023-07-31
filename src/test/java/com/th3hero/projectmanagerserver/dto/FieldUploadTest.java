package com.th3hero.projectmanagerserver.dto;

import com.th3hero.projectmanagerserver.TestEntities;
import com.th3hero.projectmanagerserver.entities.FieldJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class FieldUploadTest {

    @Test
    void customGetters() {
        final var title = "TestTitle";
        final var content = "TestContent";
        final var fieldUpload = new FieldUpload(title, content);

        assertThat(fieldUpload.title()).isEqualTo(title);
        assertThat(fieldUpload.content()).isEqualTo(content);
    }


    /**
     * We never want to return null fields to the user.
     * So anytime they upload a dto we will use default values if a field is left null.
     * This is a test of the custom getters for fieldUpload
     */
    @Test
    void customGetters_nullFields() {
        final var fieldUpload = new FieldUpload(null, null);

        assertThat(fieldUpload.title()).isNotNull();
        assertThat(fieldUpload.content()).isNotNull();
    }

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

        assertThat(result.getTitle()).isNotNull();
        assertThat(result.getContent()).isNotNull();
        assertThat(result.getProject()).isEqualTo(projectJpa);
        assertThat(result).isInstanceOf(FieldJpa.class);
    }

}
