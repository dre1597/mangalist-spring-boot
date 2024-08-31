package org.example.mangalistspringboot.domain.entities;

import org.example.mangalistspringboot.infra.api.dto.requests.UpdateMangaRequest;
import org.example.mangalistspringboot.infra.api.dto.responses.MangaResponse;
import org.example.mangalistspringboot.infra.persistence.MangaJpaEntity;

import java.time.Instant;
import java.util.UUID;

public class Manga {
  private UUID id = UUID.randomUUID();
  private String name;
  private MangaStatus status = MangaStatus.PUBLISHING;
  private Double currentChapter;
  private Double finalChapter;
  private Double englishChapter;
  private Double portugueseChapter;
  private String extraInfo;
  private String alternativeName;
  private Instant createdAt = Instant.now();
  private Instant updatedAt = Instant.now();

  private Manga(
      final String name,
      final MangaStatus status,
      final Double currentChapter,
      final Double finalChapter,
      final Double englishChapter,
      final Double portugueseChapter,
      final String extraInfo,
      final String alternativeName
  ) {
    this.name = name;
    this.status = status;
    this.currentChapter = currentChapter;
    this.finalChapter = finalChapter;
    this.englishChapter = englishChapter;
    this.portugueseChapter = portugueseChapter;
    this.extraInfo = extraInfo;
    this.alternativeName = alternativeName;
  }

  private Manga(
      final UUID id,
      final String name,
      final MangaStatus status,
      final Double currentChapter,
      final Double finalChapter,
      final Double englishChapter,
      final Double portugueseChapter,
      final String extraInfo,
      final String alternativeName,
      final Instant createdAt,
      final Instant updatedAt
  ) {
    this.id = id;
    this.name = name;
    this.status = status;
    this.currentChapter = currentChapter;
    this.finalChapter = finalChapter;
    this.englishChapter = englishChapter;
    this.portugueseChapter = portugueseChapter;
    this.extraInfo = extraInfo;
    this.alternativeName = alternativeName;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static Manga newManga(
      final String name,
      final MangaStatus status,
      final Double currentChapter,
      final Double finalChapter,
      final Double englishChapter,
      final Double portugueseChapter,
      final String extraInfo,
      final String alternativeName
  ) {
    var manga = new Manga(
        name,
        status,
        currentChapter,
        finalChapter,
        englishChapter,
        portugueseChapter,
        extraInfo,
        alternativeName
    );
    manga.validate();
    return manga;
  }

  public static Manga fromJpaEntity(final MangaJpaEntity mangaJpaEntity) {
    return new Manga(
        mangaJpaEntity.getId(),
        mangaJpaEntity.getName(),
        mangaJpaEntity.getStatus(),
        mangaJpaEntity.getCurrentChapter(),
        mangaJpaEntity.getFinalChapter(),
        mangaJpaEntity.getEnglishChapter(),
        mangaJpaEntity.getPortugueseChapter(),
        mangaJpaEntity.getExtraInfo(),
        mangaJpaEntity.getAlternativeName(),
        mangaJpaEntity.getCreatedAt(),
        mangaJpaEntity.getUpdatedAt()
    );
  }

  public Manga update(final UpdateMangaRequest request) {
    this.name = request.name().orElse(this.name);
    this.currentChapter = request.currentChapter().orElse(this.currentChapter);
    this.finalChapter = request.finalChapter().orElse(this.finalChapter);
    this.englishChapter = request.englishChapter().orElse(this.englishChapter);
    this.portugueseChapter = request.portugueseChapter().orElse(this.portugueseChapter);
    this.extraInfo = request.extraInfo().orElse(this.extraInfo);
    this.alternativeName = request.alternativeName().orElse(this.alternativeName);
    this.updatedAt = Instant.now();
    this.validate();
    return this;
  }

  private void validate() {
    if (this.name == null || this.name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }

    if (this.currentChapter == null || this.currentChapter <= 0) {
      throw new IllegalArgumentException("Current chapter cannot be null, zero or negative");
    }

    if (this.finalChapter != null && this.finalChapter <= 0) {
      throw new IllegalArgumentException("Final chapter cannot be zero or negative");
    }

    if (this.finalChapter != null && this.currentChapter > this.finalChapter) {
      throw new IllegalArgumentException("Current chapter cannot be greater than final chapter");
    }

    if (this.englishChapter != null && this.englishChapter <= 0) {
      throw new IllegalArgumentException("English chapter cannot be zero or negative");
    }

    if (this.portugueseChapter != null && this.portugueseChapter <= 0) {
      throw new IllegalArgumentException("Portuguese chapter cannot be zero or negative");
    }
  }

  public MangaJpaEntity toJpaEntity() {
    return new MangaJpaEntity(
        this.id,
        this.name,
        this.status,
        this.currentChapter,
        this.finalChapter,
        this.englishChapter,
        this.portugueseChapter,
        this.extraInfo,
        this.alternativeName,
        this.createdAt,
        this.updatedAt
    );
  }

  public MangaResponse toResponse() {
    return new MangaResponse(
        this.id,
        this.name,
        this.status,
        this.currentChapter,
        this.finalChapter,
        this.englishChapter,
        this.portugueseChapter,
        this.extraInfo,
        this.alternativeName,
        this.createdAt,
        this.updatedAt
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

  public Instant getUpdatedAt() {
    return updatedAt;
  }
}
