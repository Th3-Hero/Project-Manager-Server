package com.th3hero.projectmanagerserver.controllers;

import com.th3hero.projectmanagerserver.dto.Field;
import com.th3hero.projectmanagerserver.dto.FieldUpload;
import com.th3hero.projectmanagerserver.services.FieldService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/field")
@Tag(name = "Field Controller", description = "Handles all operations regarding Fields")
public class FieldController {
    private final FieldService fieldService;

    @GetMapping("/{projectId}")
    @Operation(summary = "Get a list of Fields on a project")
    public Collection<Field> getFieldsOnProject(
        @PathVariable UUID projectId
    ) {
        return fieldService.getFieldsOnProject(projectId);
    }

    @PostMapping("/create/{projectId}")
    @Operation(summary = "Create a new Field")
    @ResponseStatus(HttpStatus.CREATED)
    public Field createField(
        @PathVariable UUID projectId,
        @RequestBody FieldUpload fieldUpload
    ) {
        return fieldService.createField(projectId, fieldUpload);
    }

    @PostMapping("/{projectId}/{fieldId}")
    @Operation(summary = "Update an existing Field")
    public Field updateField(
        @PathVariable UUID projectId,
        @PathVariable UUID fieldId,
        @RequestBody FieldUpload fieldUpload
    ) {
        return fieldService.updateField(fieldId, projectId, fieldUpload);
    }

    @DeleteMapping("/{projectId}/{fieldId}")
    @Operation(summary = "Delete a Field by its id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteField(
        @PathVariable UUID projectId,
        @PathVariable UUID fieldId
    ) {
        fieldService.deleteField(projectId, fieldId);
    }

}