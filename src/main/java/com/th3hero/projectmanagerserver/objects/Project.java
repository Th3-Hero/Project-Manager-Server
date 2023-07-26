package com.th3hero.projectmanagerserver.objects;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project {
    private Optional<String> name;
    private Optional<String> description;
}