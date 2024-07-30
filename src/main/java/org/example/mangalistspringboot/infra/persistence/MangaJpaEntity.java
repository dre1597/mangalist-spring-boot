package org.example.mangalistspringboot.infra.persistence;

import jakarta.persistence.*;
import org.example.mangalistspringboot.domain.entities.Manga;
import org.example.mangalistspringboot.domain.entities.MangaStatus;
import org.example.mangalistspringboot.infra.api.dto.responses.MangaResponse;

import java.time.Instant;
import java.util.UUID;

@Entity(name = "mangas")
@Table()
public class MangaJpaEntity {
  @Id()
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Column(nullable = false, unique = true)
  private String name;
  @Column(nullable = false)
  private Double currentChapter;
  @Column
  private Double finalChapter;
  @Column
  private MangaStatus status = MangaStatus.PUBLISHING;
  @Column
  private Double englishChapter;
  @Column
  private Double portugueseChapter;
  @Column
  private String extraInfo;
  @Column
  private String alternativeName;
  @Column
  private Instant createdAt = Instant.now();

  public MangaJpaEntity(
      final UUID id,
      final String name,
      final Double currentChapter,
      final Double finalChapter,
      final MangaStatus status,
      final Double englishChapter,
      final Double portugueseChapter,
      final String extraInfo,
      final String alternativeName,
      final Instant createdAt
  ) {
    this.id = id;
    this.name = name;
    this.currentChapter = currentChapter;
    this.finalChapter = finalChapter;
    this.status = status;
    this.englishChapter = englishChapter;
    this.portugueseChapter = portugueseChapter;
    this.extraInfo = extraInfo;
    this.alternativeName = alternativeName;
    this.createdAt = createdAt;
  }

  public MangaJpaEntity(
      final String name,
      final Double currentChapter,
      final Double finalChapter,
      final MangaStatus status,
      final Double englishChapter,
      final Double portugueseChapter,
      final String extraInfo,
      final String alternativeName
  ) {
    this.name = name;
    this.currentChapter = currentChapter;
    this.finalChapter = finalChapter;
    this.status = status;
    this.englishChapter = englishChapter;
    this.portugueseChapter = portugueseChapter;
    this.extraInfo = extraInfo;
    this.alternativeName = alternativeName;
  }

  public MangaJpaEntity() {
  }

  public static MangaJpaEntity fromEntity(final Manga manga) {
    return new MangaJpaEntity(
        manga.getId(),
        manga.getName(),
        manga.getCurrentChapter(),
        manga.getFinalChapter(),
        manga.getStatus(),
        manga.getEnglishChapter(),
        manga.getPortugueseChapter(),
        manga.getExtraInfo(),
        manga.getAlternativeName(),
        manga.getCreatedAt()
    );
  }

  public UUID getId() {
    return id;
  }

  public void setId(final UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public Double getCurrentChapter() {
    return currentChapter;
  }

  public void setCurrentChapter(final Double currentChapter) {
    this.currentChapter = currentChapter;
  }

  public Double getFinalChapter() {
    return finalChapter;
  }

  public void setFinalChapter(final Double finalChapter) {
    this.finalChapter = finalChapter;
  }

  public MangaStatus getStatus() {
    return status;
  }

  public void setStatus(final MangaStatus status) {
    this.status = status;
  }

  public Double getEnglishChapter() {
    return englishChapter;
  }

  public void setEnglishChapter(final Double englishChapter) {
    this.englishChapter = englishChapter;
  }

  public double getPortugueseChapter() {
    return portugueseChapter;
  }

  public void setPortugueseChapter(final Double portugueseChapter) {
    this.portugueseChapter = portugueseChapter;
  }

  public String getExtraInfo() {
    return extraInfo;
  }

  public void setExtraInfo(final String extraInfo) {
    this.extraInfo = extraInfo;
  }

  public String getAlternativeName() {
    return alternativeName;
  }

  public void setAlternativeName(final String alternativeName) {
    this.alternativeName = alternativeName;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(final Instant createdAt) {
    this.createdAt = createdAt;
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
}
