package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.api.dto.requests.CreateMangaRequest;
import org.example.mangalistspringboot.domain.repositories.MangaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CreateMangaUseCase {
  private final MangaRepository mangaRepository;

  public CreateMangaUseCase(final MangaRepository mangaRepository) {
    this.mangaRepository = Objects.requireNonNull(mangaRepository);
  }

  public void execute(final CreateMangaRequest request) {
    this.mangaRepository.save(request.toEntity());
  }
}
