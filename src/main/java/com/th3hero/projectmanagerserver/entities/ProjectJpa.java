package com.th3hero.projectmanagerserver.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.th3hero.projectmanagerserver.objects.Project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectJpa implements Serializable {

    @Id
    @Column
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FieldJpa> fields;

    @Setter(AccessLevel.NONE)
    @JoinTable(
        name = "project_tag",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TagJpa> tags;

    public static ProjectJpa create(String name, String description) {
        return builder()
            .name(name)
            .description(description)
            .build();
    }

    public Project convertToDTO() {
        return new Project(
            this.getId(),
            this.getName(),
            this.getDescription(),
            this.getFields().stream().map(field -> field.convertToDTO()).toList(),
            this.getTags().stream().map(tag -> tag.convertToDTO()).toList()
        );
    }

    public ProjectJpa convertToJpa(Project project) {
        return new ProjectJpa(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getFields().stream().map(field -> field.convertToJpa()).toList(),
            project.getTags().stream().map(tag -> tag.convertToJpa()).toList()
        );
    }

}