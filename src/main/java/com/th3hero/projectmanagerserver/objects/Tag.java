package com.th3hero.projectmanagerserver.objects;

import java.io.Serializable;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag implements Serializable {

    private UUID id;
    private String name;
    private String hexColor;
}