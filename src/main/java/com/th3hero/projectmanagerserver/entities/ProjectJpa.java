package com.th3hero.projectmanagerserver.entities;

import com.th3hero.projectmanagerserver.dto.Project;
import com.th3hero.projectmanagerserver.utils.CollectionUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

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

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String description;

    @NotNull
    @Builder.Default
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<FieldJpa> fields = new ArrayList<>();

    @NotNull
    @Builder.Default
    @Setter(AccessLevel.NONE)
    @JoinTable(
        name = "project_tag",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Collection<TagJpa> tags = new ArrayList<>();

    public void setFields(Collection<FieldJpa> fields) {
        CollectionUtils.replaceList(this.fields, fields);
    }

    public void setTags(Collection<TagJpa> tags) {
        CollectionUtils.replaceList(this.tags, tags);
    }

    public Project convertToDto() {
        return new Project(
            this.getId(),
            this.getName(),
            this.getDescription(),
            this.getFields().stream().map(field -> field.convertToDto()).toList(),
            this.getTags().stream().map(tag -> tag.convertToDto()).toList()
        );
    }

}