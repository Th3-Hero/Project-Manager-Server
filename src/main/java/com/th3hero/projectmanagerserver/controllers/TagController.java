package com.th3hero.projectmanagerserver.controllers;

import java.util.Collection;
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

import com.th3hero.projectmanagerserver.dto.Project;
import com.th3hero.projectmanagerserver.dto.Tag;
import com.th3hero.projectmanagerserver.dto.TagUpload;
import com.th3hero.projectmanagerserver.services.TagService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @GetMapping("/list")
    public Collection<Tag> listTags() {
        return tagService.listTags();
    }

    @GetMapping("/{projectId}")
    public Collection<Tag> getTagsOnProject(
        @PathVariable UUID projectId
    ) {
        return tagService.getTagsOnProject(projectId);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Tag createTag(
        @RequestBody TagUpload tag
    ) {
        return tagService.createTag(tag);
    }

    @PostMapping("/{tagId}")
    public Tag updateTag(
        @PathVariable UUID tagId,
        @RequestBody TagUpload tag
    ) {
        return tagService.updateTag(tagId, tag);
    }

    @DeleteMapping("/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagById(
        @PathVariable UUID tagId
    ) {
        tagService.deleteTagById(tagId);
    }

    @PostMapping("/{tagId}/{projectId}")
    public Project addTagToProject(
        @PathVariable UUID tagId,
        @PathVariable UUID projectId
    ) {
        return tagService.addTagToProject(tagId, projectId);
    }
}