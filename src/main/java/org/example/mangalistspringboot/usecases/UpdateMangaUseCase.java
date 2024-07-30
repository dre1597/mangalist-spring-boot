package org.example.mangalistspringboot.usecases;

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
    final var manga = this.mangaJpaRepository.findById(id).orElseThrow();

    request.name().ifPresent(manga::setName);
    request.currentChapter().ifPresent(manga::setCurrentChapter);
    request.finalChapter().ifPresent(manga::setFinalChapter);
    request.status().ifPresent(manga::setStatus);
    request.englishChapter().ifPresent(manga::setEnglishChapter);
    request.portugueseChapter().ifPresent(manga::setPortugueseChapter);
    request.extraInfo().ifPresent(manga::setExtraInfo);
    request.alternativeName().ifPresent(manga::setAlternativeName);

    this.mangaJpaRepository.save(manga);
  }
}
