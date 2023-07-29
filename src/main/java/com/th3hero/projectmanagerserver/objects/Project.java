package com.th3hero.projectmanagerserver.objects;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Project implements Serializable {

    private UUID id;

    @Builder.Default
    private String name = "New Project";
    @Builder.Default
    private String description = "";

    private List<Field> fields;
    private List<Tag> tags;

}