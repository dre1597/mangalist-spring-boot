package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.api.dto.responses.MangaResponse;
import org.example.mangalistspringboot.domain.repositories.MangaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class GetOneMangaUseCase {

  private final MangaRepository mangaRepository;

  public GetOneMangaUseCase(final MangaRepository mangaRepository) {
    this.mangaRepository = Objects.requireNonNull(mangaRepository);
  }

  public MangaResponse execute(final UUID id) {
    System.out.println(id);
    final var manga = this.mangaRepository.findById(id).orElseThrow();
    return manga.toResponse();
  }
}
