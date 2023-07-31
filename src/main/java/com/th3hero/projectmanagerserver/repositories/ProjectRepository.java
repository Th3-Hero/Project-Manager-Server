package com.th3hero.projectmanagerserver.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.th3hero.projectmanagerserver.entities.ProjectJpa;


public interface ProjectRepository extends JpaRepository<ProjectJpa, UUID> {

    List<ProjectJpa> findAllByTagsId(UUID tagId);
}