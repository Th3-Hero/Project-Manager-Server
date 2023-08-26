package com.th3hero.projectmanagerserver.repositories;

import com.th3hero.projectmanagerserver.entities.TagJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<TagJpa, UUID> {

}