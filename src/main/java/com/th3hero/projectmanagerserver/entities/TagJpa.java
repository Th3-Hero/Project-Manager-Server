package com.th3hero.projectmanagerserver.entities;

import java.io.Serializable;
import java.util.UUID;

import com.th3hero.projectmanagerserver.objects.Tag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

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

    // public Tag convertToDTO() {
    //     return new Tag(
    //         this.getId(),
    //         this.getName(),
    //         this.getHexColor()
    //     );
    // }

    // public TagJpa convertToJpa(Tag tag) {
    //     return new TagJpa(
    //         tag.getId(),
    //         tag.getName(),
    //         tag.getHexColor()
    //     );
    // }

}
