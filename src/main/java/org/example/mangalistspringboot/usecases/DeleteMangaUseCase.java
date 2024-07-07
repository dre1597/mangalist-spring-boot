package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.domain.repositories.MangaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class DeleteMangaUseCase {

  private final MangaRepository mangaRepository;

  public DeleteMangaUseCase(final MangaRepository mangaRepository) {
    this.mangaRepository = Objects.requireNonNull(mangaRepository);
  }

  public void execute(final UUID id) {
    this.mangaRepository.findById(id).ifPresent(mangaRepository::delete);
  }
}
