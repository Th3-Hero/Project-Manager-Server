package com.th3hero.projectmanagerserver.entities;

import com.th3hero.projectmanagerserver.dto.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name = "tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TagJpa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column
    @NonNull
    private String name;

    @Column
    @NonNull
    private String hexColor;

    public Tag convertToDto() {
        return new Tag(
            this.getId(),
            this.getName(),
            this.getHexColor()
        );
    }
}
