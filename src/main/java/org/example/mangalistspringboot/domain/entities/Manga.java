package org.example.mangalistspringboot.domain.entities;

import org.example.mangalistspringboot.infra.api.dto.responses.MangaResponse;
import org.example.mangalistspringboot.infra.persistence.MangaJpaEntity;

import java.time.Instant;
import java.util.UUID;

public class Manga {
  private final MangaStatus status = MangaStatus.PUBLISHING;
  private final Instant createdAt = Instant.now();
  private final UUID id = UUID.randomUUID();
  private String name;
  private Double currentChapter;
  private Double finalChapter;
  private Double englishChapter;
  private Double portugueseChapter;
  private String extraInfo;
  private String alternativeName;

  private Manga(
      final String name,
      final Double currentChapter,
      final Double finalChapter,
      final Double englishChapter,
      final Double portugueseChapter,
      final String extraInfo,
      final String alternativeName
  ) {
    this.name = name;
    this.currentChapter = currentChapter;
    this.finalChapter = finalChapter;
    this.englishChapter = englishChapter;
    this.portugueseChapter = portugueseChapter;
    this.extraInfo = extraInfo;
    this.alternativeName = alternativeName;
  }

  public static Manga newManga(
      final String name,
      final Double currentChapter,
      final Double finalChapter,
      final Double englishChapter,
      final Double portugueseChapter,
      final String extraInfo,
      final String alternativeName
  ) {
    return new Manga(
        name,
        currentChapter,
        finalChapter,
        englishChapter,
        portugueseChapter,
        extraInfo,
        alternativeName
    );
  }

  public Manga fromJpaEntity(final MangaJpaEntity mangaJpaEntity) {
    this.name = mangaJpaEntity.getName();
    this.currentChapter = mangaJpaEntity.getCurrentChapter();
    this.finalChapter = mangaJpaEntity.getFinalChapter();
    this.englishChapter = mangaJpaEntity.getEnglishChapter();
    this.portugueseChapter = mangaJpaEntity.getPortugueseChapter();
    this.extraInfo = mangaJpaEntity.getExtraInfo();
    this.alternativeName = mangaJpaEntity.getAlternativeName();
    return this;
  }

  public MangaJpaEntity toJpaEntity() {
    return new MangaJpaEntity(
        this.id,
        this.name,
        this.currentChapter,
        this.finalChapter,
        this.status,
        this.englishChapter,
        this.portugueseChapter,
        this.extraInfo,
        this.alternativeName,
        this.createdAt
    );
  }

  public MangaResponse toResponse() {
    return new MangaResponse(
        this.id,
        this.name,
        this.currentChapter,
        this.finalChapter,
        this.status,
        this.englishChapter,
        this.portugueseChapter,
        this.extraInfo,
        this.alternativeName,
        this.createdAt
    );
  }

  public MangaStatus getStatus() {
    return status;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Double getCurrentChapter() {
    return currentChapter;
  }

  public Double getFinalChapter() {
    return finalChapter;
  }

  public Double getEnglishChapter() {
    return englishChapter;
  }

  public Double getPortugueseChapter() {
    return portugueseChapter;
  }

  public String getExtraInfo() {
    return extraInfo;
  }

  public String getAlternativeName() {
    return alternativeName;
  }
}
