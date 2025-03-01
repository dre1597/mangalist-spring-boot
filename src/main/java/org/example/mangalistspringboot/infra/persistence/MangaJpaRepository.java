package org.example.mangalistspringboot.infra.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MangaJpaRepository extends JpaRepository<MangaJpaEntity, UUID> {
  Page<MangaJpaEntity> findAll(Specification<MangaJpaEntity> whereClause, Pageable page);

  boolean existsByName(String name);
}
