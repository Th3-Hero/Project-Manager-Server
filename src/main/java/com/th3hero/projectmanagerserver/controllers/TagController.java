package com.th3hero.projectmanagerserver.controllers;

import com.th3hero.projectmanagerserver.dto.Project;
import com.th3hero.projectmanagerserver.dto.Tag;
import com.th3hero.projectmanagerserver.dto.TagUpload;
import com.th3hero.projectmanagerserver.services.TagService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag Controller", description = "Handles all operations regarding Tags")
public class TagController {
    private final TagService tagService;

    @GetMapping("/list")
    @Operation(summary = "Get a list of available tags")
    public Collection<Tag> listTags() {
        return tagService.listTags();
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "Get a list of tags that are applied to a specific project")
    public Collection<Tag> getTagsOnProject(
        @PathVariable @NotBlank(message = "A project id is required") UUID projectId
    ) {
        return tagService.getTagsOnProject(projectId);
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new tag")
    @ResponseStatus(HttpStatus.CREATED)
    public Tag createTag(
        @RequestBody TagUpload tag
    ) {
        return tagService.createTag(tag);
    }

    @PostMapping("/{tagId}")
    @Operation(summary = "Edit an existing tag")
    public Tag updateTag(
        @PathVariable @NotBlank(message = "Tag id is required") UUID tagId,
        @RequestBody TagUpload tag
    ) {
        return tagService.updateTag(tagId, tag);
    }

    @DeleteMapping("/{tagId}")
    @Operation(summary = "Delete a tag by it's id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagById(
        @PathVariable @NotBlank(message = "Tag id is required") UUID tagId
    ) {
        tagService.deleteTagById(tagId);
    }

    @Operation(summary = "Add an existing Tag to an existing Project")
    @PostMapping("/{tagId}/{projectId}")
    public Project addTagToProject(
        @PathVariable @NotBlank(message = "Tag id is required") UUID tagId,
        @PathVariable @NotBlank(message = "Project id is required") UUID projectId
    ) {
        return tagService.addTagToProject(tagId, projectId);
    }
}