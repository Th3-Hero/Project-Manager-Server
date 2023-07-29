package com.th3hero.projectmanagerserver.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.th3hero.projectmanagerserver.dto.Field;
import com.th3hero.projectmanagerserver.services.FieldService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/field")
public class FieldController {
    private final FieldService fieldService;

    @GetMapping("/fields/{projectId}")
    public List<Field> getFieldsOnProject(
        @PathVariable UUID projectId
    ) {
        return fieldService.getFieldsOnProject(projectId);
    }

    // TODO: Create field on project


    @DeleteMapping("/{projectId}/{fieldId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteField(
        @PathVariable UUID projectId,
        @PathVariable UUID fieldId
    ) {
        fieldService.deleteField(projectId, fieldId);
    }

    // @PostMapping("/")
    // public Field updateField(
    //     @RequestBody Field field
    // ) {
    //     return fieldService.updateField(field);
    // }
}