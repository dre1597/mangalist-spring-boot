package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.infra.api.dto.responses.MangaResponse;
import org.example.mangalistspringboot.infra.persistence.MangaJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class GetOneMangaUseCase {

  private final MangaJpaRepository mangaJpaRepository;

  public GetOneMangaUseCase(final MangaJpaRepository mangaJpaRepository) {
    this.mangaJpaRepository = Objects.requireNonNull(mangaJpaRepository);
  }

  public MangaResponse execute(final UUID id) {
    final var manga = this.mangaJpaRepository.findById(id).orElseThrow();
    return manga.toResponse();
  }
}
