package org.example.mangalistspringboot.api.controllers;

import jakarta.validation.Valid;
import org.example.mangalistspringboot.api.MangaAPI;
import org.example.mangalistspringboot.api.dto.requests.CreateMangaRequest;
import org.example.mangalistspringboot.api.dto.responses.ListMangasResponse;
import org.example.mangalistspringboot.domain.helpers.Pagination;
import org.example.mangalistspringboot.domain.helpers.SearchQuery;
import org.example.mangalistspringboot.usecases.CreateMangaUseCase;
import org.example.mangalistspringboot.usecases.ListMangasUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MangaController implements MangaAPI {
  private final ListMangasUseCase listMangasUseCase;
  private final CreateMangaUseCase createMangaUseCase;

  public MangaController(final ListMangasUseCase listMangasUseCase, final CreateMangaUseCase createMangaUseCase) {
    this.listMangasUseCase = listMangasUseCase;
    this.createMangaUseCase = createMangaUseCase;
  }

  @Override
  public Pagination<ListMangasResponse> list(
      final String search,
      final int page,
      final int perPage,
      final String sort,
      final String direction) {
    var searchQuery = new SearchQuery(page, perPage, search, sort, direction);
    return this.listMangasUseCase.execute(searchQuery);
  }

  @Override
  public void add(@Valid final CreateMangaRequest request) {
    this.createMangaUseCase.execute(request);
  }
}
