package org.example.mangalistspringboot.infra.api.controllers;

import org.example.mangalistspringboot.domain.helpers.Pagination;
import org.example.mangalistspringboot.domain.helpers.SearchQuery;
import org.example.mangalistspringboot.infra.api.MangaAPI;
import org.example.mangalistspringboot.infra.api.dto.requests.CreateMangaRequest;
import org.example.mangalistspringboot.infra.api.dto.requests.UpdateMangaRequest;
import org.example.mangalistspringboot.infra.api.dto.responses.MangaResponse;
import org.example.mangalistspringboot.usecases.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
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
    this.listMangasUseCase = Objects.requireNonNull(listMangasUseCase);
    this.createMangaUseCase = Objects.requireNonNull(createMangaUseCase);
    this.getOneMangaUseCase = Objects.requireNonNull(getOneMangaUseCase);
    this.updateMangaUseCase = Objects.requireNonNull(updateMangaUseCase);
    this.deleteMangaUseCase = Objects.requireNonNull(deleteMangaUseCase);
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
  public ResponseEntity<Object> add(final CreateMangaRequest request) {
    this.createMangaUseCase.execute(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }

  @Override
  public MangaResponse findOne(final String id) {
    return this.getOneMangaUseCase.execute(UUID.fromString(id));
  }

  @Override
  public ResponseEntity<Object> update(final String id, final UpdateMangaRequest request) {
    this.updateMangaUseCase.execute(UUID.fromString(id), request);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @Override
  public ResponseEntity<Object> delete(final String id) {
    this.deleteMangaUseCase.execute(UUID.fromString(id));
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }
}
