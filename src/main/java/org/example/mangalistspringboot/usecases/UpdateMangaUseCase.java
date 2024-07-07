package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.api.dto.requests.UpdateMangaRequest;
import org.example.mangalistspringboot.domain.repositories.MangaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;


@Service
public class UpdateMangaUseCase {
  private final MangaRepository mangaRepository;

  public UpdateMangaUseCase(final MangaRepository mangaRepository) {
    this.mangaRepository = Objects.requireNonNull(mangaRepository);
  }

  public void execute(final UUID id, final UpdateMangaRequest request) {
    final var manga = this.mangaRepository.findById(id).orElseThrow();

    request.name().ifPresent(manga::setName);
    request.currentChapter().ifPresent(manga::setCurrentChapter);
    request.finalChapter().ifPresent(manga::setFinalChapter);
    request.status().ifPresent(manga::setStatus);
    request.englishChapter().ifPresent(manga::setEnglishChapter);
    request.portugueseChapter().ifPresent(manga::setPortugueseChapter);
    request.extraInfo().ifPresent(manga::setExtraInfo);
    request.alternativeName().ifPresent(manga::setAlternativeName);

    this.mangaRepository.save(manga);
  }
}
