package com.th3hero.projectmanagerserver.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.th3hero.projectmanagerserver.dto.Tag;
import com.th3hero.projectmanagerserver.services.TagService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @GetMapping("/{projectId}")
    public List<Tag> getTagsOnProject(
        @PathVariable UUID projectId
    ) {
        return tagService.getTagsOnProject(projectId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{tagId}")
    public void deleteTagById(
        @PathVariable UUID tagId
    ) {
        tagService.deleteTagById(tagId);
    }

    // TODO: Create tag


    @PostMapping("/")
    public Tag updateTag(
        @RequestBody Tag tag
    ) {
        return tagService.updateTag(tag);
    }
}