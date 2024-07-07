package org.example.mangalistspringboot.api.controllers;

import org.example.mangalistspringboot.api.MangaAPI;
import org.example.mangalistspringboot.api.dto.requests.CreateMangaRequest;
import org.example.mangalistspringboot.api.dto.requests.UpdateMangaRequest;
import org.example.mangalistspringboot.api.dto.responses.MangaResponse;
import org.example.mangalistspringboot.domain.helpers.Pagination;
import org.example.mangalistspringboot.domain.helpers.SearchQuery;
import org.example.mangalistspringboot.usecases.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MangaController implements MangaAPI {
  private final ListMangasUseCase listMangasUseCase;
  private final CreateMangaUseCase createMangaUseCase;
  private final GetOneMangaUseCase getOneMangaUseCase;
  private final UpdateMangaUseCase updateMangaUseCase;
  private final DeleteMangaUseCase deleteMangaUseCase;

  public MangaController(
      final ListMangasUseCase listMangasUseCase,
      final CreateMangaUseCase createMangaUseCase,
      final GetOneMangaUseCase getOneMangaUseCase,
      final UpdateMangaUseCase updateMangaUseCase,
      final DeleteMangaUseCase deleteMangaUseCase
  ) {
    this.listMangasUseCase = listMangasUseCase;
    this.createMangaUseCase = createMangaUseCase;
    this.getOneMangaUseCase = getOneMangaUseCase;
    this.updateMangaUseCase = updateMangaUseCase;
    this.deleteMangaUseCase = deleteMangaUseCase;
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
  public void add(final CreateMangaRequest request) {
    this.createMangaUseCase.execute(request);
  }

  @Override
  public MangaResponse findOne(final String id) {
    return this.getOneMangaUseCase.execute(UUID.fromString(id));
  }

  @Override
  public void update(final String id, final UpdateMangaRequest request) {
    this.updateMangaUseCase.execute(UUID.fromString(id), request);
  }

  @Override
  public void delete(final String id) {
    this.deleteMangaUseCase.execute(UUID.fromString(id));
  }
}
