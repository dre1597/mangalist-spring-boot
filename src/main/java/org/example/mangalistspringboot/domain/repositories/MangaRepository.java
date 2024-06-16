package org.example.mangalistspringboot.domain.repositories;

import org.example.mangalistspringboot.domain.entities.Manga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MangaRepository extends JpaRepository<Manga, UUID> {
  Page<Manga> findAll(Specification<Manga> whereClause, Pageable page);
}
