package com.th3hero.projectmanagerserver.repositories;

import com.th3hero.projectmanagerserver.entities.ProjectJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface ProjectRepository extends JpaRepository<ProjectJpa, UUID> {

    List<ProjectJpa> findAllByTagsId(UUID tagId);
}