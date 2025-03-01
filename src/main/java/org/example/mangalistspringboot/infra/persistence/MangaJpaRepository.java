package org.example.mangalistspringboot.infra.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MangaJpaRepository extends JpaRepository<MangaJpaEntity, UUID> {
  Page<MangaJpaEntity> findAll(final Specification<MangaJpaEntity> whereClause, final Pageable page);

  boolean existsByName(final String name);
}
