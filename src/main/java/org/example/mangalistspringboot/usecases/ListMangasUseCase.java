package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.domain.entities.Manga;
import org.example.mangalistspringboot.domain.helpers.Pagination;
import org.example.mangalistspringboot.domain.helpers.SearchQuery;
import org.example.mangalistspringboot.domain.utils.SpecificationUtils;
import org.example.mangalistspringboot.infra.api.dto.responses.MangaResponse;
import org.example.mangalistspringboot.infra.persistence.MangaJpaEntity;
import org.example.mangalistspringboot.infra.persistence.MangaJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class ListMangasUseCase {

  private final MangaJpaRepository mangaJpaRepository;

  public ListMangasUseCase(final MangaJpaRepository mangaJpaRepository) {
    this.mangaJpaRepository = Objects.requireNonNull(mangaJpaRepository);
  }

  public Pagination<MangaResponse> execute(SearchQuery searchQuery) {
    final var page = PageRequest.of(
        searchQuery.page(),
        searchQuery.perPage(),
        Sort.by(Sort.Direction.fromString(searchQuery.direction()), searchQuery.sort())
    );
    final var where = Optional.ofNullable(searchQuery.terms())
        .filter(str -> !str.isBlank())
        .map(this::assembleSpecification)
        .orElse(null);
    
    final var pageResult =
        this.mangaJpaRepository.findAll(where(where), page);

    return new Pagination<>(
        pageResult.getNumber(),
        pageResult.getSize(),
        pageResult.getTotalElements(),
        pageResult.map(Manga::fromJpaEntity).map(Manga::toResponse).toList()
    );
  }

  private Specification<MangaJpaEntity> assembleSpecification(final String terms) {
    var fields = List.of("name", "alternativeName");
    return SpecificationUtils.likeMultiple(fields, terms);
  }
}
