package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.infra.persistence.MangaJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class DeleteMangaUseCase {

  private final MangaJpaRepository mangaJpaRepository;

  public DeleteMangaUseCase(final MangaJpaRepository mangaJpaRepository) {
    this.mangaJpaRepository = Objects.requireNonNull(mangaJpaRepository);
  }

  public void execute(final UUID id) {
    this.mangaJpaRepository.findById(id).ifPresent(mangaJpaRepository::delete);
  }
}
