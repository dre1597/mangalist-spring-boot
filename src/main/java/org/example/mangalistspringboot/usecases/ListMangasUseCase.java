package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.api.dto.responses.ListMangasResponse;
import org.example.mangalistspringboot.domain.entities.Manga;
import org.example.mangalistspringboot.domain.helpers.Pagination;
import org.example.mangalistspringboot.domain.helpers.SearchQuery;
import org.example.mangalistspringboot.domain.repositories.MangaRepository;
import org.example.mangalistspringboot.domain.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class ListMangasUseCase {

  private final MangaRepository mangaRepository;

  public ListMangasUseCase(final MangaRepository mangaRepository) {
    this.mangaRepository = Objects.requireNonNull(mangaRepository);
  }

  public Pagination<ListMangasResponse> execute(SearchQuery searchQuery) {
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
        this.mangaRepository.findAll(where(where), page);

    return new Pagination<>(
        pageResult.getNumber(),
        pageResult.getSize(),
        pageResult.getTotalElements(),
        pageResult.map(Manga::toResponse).toList()
    );
  }

  private Specification<Manga> assembleSpecification(final String terms) {
    return SpecificationUtils.like("name", terms);
  }
}
