package com.th3hero.projectmanagerserver.entities;

import com.th3hero.projectmanagerserver.dto.Field;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name = "field")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FieldJpa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectJpa project;

    @Column
    private String title;

    @Column
    private String content;

    public Field convertToDto () {
        return new Field(
            this.getId(),
            this.getTitle(),
            this.getContent()
        );
    }
}
