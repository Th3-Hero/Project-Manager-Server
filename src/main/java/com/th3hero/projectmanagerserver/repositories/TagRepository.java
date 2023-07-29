package com.th3hero.projectmanagerserver.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.th3hero.projectmanagerserver.entities.TagJpa;

public interface TagRepository extends JpaRepository<TagJpa, UUID> {

}