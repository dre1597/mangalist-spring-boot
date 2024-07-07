package org.example.mangalistspringboot.api.controllers;

import jakarta.validation.Valid;
import org.example.mangalistspringboot.api.MangaAPI;
import org.example.mangalistspringboot.api.dto.requests.CreateMangaRequest;
import org.example.mangalistspringboot.api.dto.responses.MangaResponse;
import org.example.mangalistspringboot.domain.helpers.Pagination;
import org.example.mangalistspringboot.domain.helpers.SearchQuery;
import org.example.mangalistspringboot.usecases.CreateMangaUseCase;
import org.example.mangalistspringboot.usecases.GetOneMangaUseCase;
import org.example.mangalistspringboot.usecases.ListMangasUseCase;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MangaController implements MangaAPI {
  private final ListMangasUseCase listMangasUseCase;
  private final CreateMangaUseCase createMangaUseCase;
  private final GetOneMangaUseCase getOneMangaUseCase;

  public MangaController(
      final ListMangasUseCase listMangasUseCase,
      final CreateMangaUseCase createMangaUseCase,
      final GetOneMangaUseCase getOneMangaUseCase
  ) {
    this.listMangasUseCase = listMangasUseCase;
    this.createMangaUseCase = createMangaUseCase;
    this.getOneMangaUseCase = getOneMangaUseCase;
  }

  @Override
  public Pagination<MangaResponse> list(
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

  @Override
  public MangaResponse findOne(final String id) {
    return this.getOneMangaUseCase.execute(UUID.fromString(id));
  }
}
