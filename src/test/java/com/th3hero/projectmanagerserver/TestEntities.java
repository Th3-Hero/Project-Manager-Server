package com.th3hero.projectmanagerserver;

import com.th3hero.projectmanagerserver.entities.FieldJpa;
import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import com.th3hero.projectmanagerserver.entities.TagJpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class TestEntities {

    public static UUID TEST_PROJECT_ID = UUID.fromString("bddd5b91-1f16-4568-a619-744a7b17f6fe");
    public static UUID TEST_FIELD_ID = UUID.fromString("d692ef6d-b8ba-4b14-a849-bdd2a9738e4c");
    public static UUID TEST_TAG_ID = UUID.fromString("d10ea4f9-ed85-4b39-a322-8debb05347c1");

    public static ProjectJpa projectJpa() {
        return ProjectJpa.builder()
                .id(TEST_PROJECT_ID)
                .name("TestName")
                .description("TestDescription")
                .build();
    }

    public static ProjectJpa projectJpa(UUID id) {
        return ProjectJpa.builder()
                .id(id)
                .name("TestName")
                .description("TestDescription")
                .build();
    }
    
    public static ProjectJpa projectJpaWithFields(UUID id, Integer numOfFields) {
        var projectJpa = TestEntities.projectJpa(id);
        projectJpa.setFields(
                TestEntities.fieldJpas(projectJpa, numOfFields)
        );
        return projectJpa;
    }

    public static ProjectJpa projectJpaWithTag(UUID id, TagJpa tagJpa, Integer extraTags) {
        List<TagJpa> tagsList = TestEntities.tagJpas(extraTags);

        tagsList.add(tagJpa);

        return ProjectJpa.builder()
                .id(id)
                .name("TestName")
                .description("TestDescription")
                .tags(tagsList)
                .build();
    }

    public static ProjectJpa projectJpaWithTags(UUID id, Integer numOfTags) {
        var projectJpa = TestEntities.projectJpa(id);
        projectJpa.setTags(
                TestEntities.tagJpas(numOfTags)
        );
        return projectJpa;
    }

    public static FieldJpa fieldJpa(ProjectJpa projectJpa) {
        return FieldJpa.builder()
                .id(TEST_FIELD_ID)
                .project(projectJpa)
                .title("TestTitle")
                .content("TestContent")
                .build();
    }

    public static FieldJpa fieldJpa(UUID id, String name,  ProjectJpa projectJpa) {
        return FieldJpa.builder()
                .id(id)
                .project(projectJpa)
                .title(name)
                .content("TestContent")
                .build();
    }

    public static List<FieldJpa> fieldJpas(ProjectJpa projectJpa, Integer size) {
        List<FieldJpa> fieldList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fieldList.add(
                    TestEntities.fieldJpa(UUID.randomUUID(), "TestName#%s".formatted(i), projectJpa)
            );
        }
        return fieldList;
    }

    public static TagJpa tagJpa() {
        return TagJpa.builder()
                .id(TEST_TAG_ID)
                .name("TestName")
                .hexColor("#TEST")
                .build();
    }

    public static TagJpa tagJpa(UUID id, String name) {
        return TagJpa.builder()
                .id(id)
                .name(name)
                .hexColor("#TEST")
                .build();
    }

    public static List<TagJpa> tagJpas(Integer size) {
        List<TagJpa> tagList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tagList.add(
                    TestEntities.tagJpa(UUID.randomUUID(), "TestName#%s".formatted(i))
            );
        }
        return tagList;
    }

    public static List<ProjectJpa> projectJpasWithTag(TagJpa tagJpa, Integer size) {
        List<ProjectJpa> projectJpaList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            projectJpaList.add(
                    TestEntities.projectJpaWithTag(UUID.randomUUID(),tagJpa, 2)
            );
        }


        return projectJpaList;
    }

}
