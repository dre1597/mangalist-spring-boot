package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.domain.entities.Manga;
import org.example.mangalistspringboot.domain.exceptions.MangaAlreadyExistsException;
import org.example.mangalistspringboot.infra.api.dto.requests.UpdateMangaRequest;
import org.example.mangalistspringboot.infra.persistence.MangaJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;


@Service
public class UpdateMangaUseCase {
  private final MangaJpaRepository mangaJpaRepository;

  public UpdateMangaUseCase(final MangaJpaRepository mangaJpaRepository) {
    this.mangaJpaRepository = Objects.requireNonNull(mangaJpaRepository);
  }

  public void execute(final UUID id, final UpdateMangaRequest request) {
    final var mangaOnDatabase = this.mangaJpaRepository.findById(id).orElseThrow();
    final var manga = Manga.fromJpaEntity(mangaOnDatabase);

    manga.update(request);

    if (!mangaOnDatabase.getName().equals(request.name()) && mangaJpaRepository.existsByName(request.name())) {
      throw new MangaAlreadyExistsException("Manga already exists");
    }

    this.mangaJpaRepository.save(manga.toJpaEntity());
  }
}
