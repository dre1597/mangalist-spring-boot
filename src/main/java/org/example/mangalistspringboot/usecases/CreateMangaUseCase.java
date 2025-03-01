package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.domain.exceptions.MangaAlreadyExistsException;
import org.example.mangalistspringboot.infra.api.dto.requests.CreateMangaRequest;
import org.example.mangalistspringboot.infra.persistence.MangaJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CreateMangaUseCase {
  private final MangaJpaRepository mangaJpaRepository;

  public CreateMangaUseCase(final MangaJpaRepository mangaJpaRepository) {
    this.mangaJpaRepository = Objects.requireNonNull(mangaJpaRepository);
  }

  public void execute(final CreateMangaRequest request) {
    var exists = this.mangaJpaRepository.existsByName(request.name());

    if (exists) {
      throw new MangaAlreadyExistsException("Manga already exists");
    }

    this.mangaJpaRepository.save(request.toEntity().toJpaEntity());
  }
}
