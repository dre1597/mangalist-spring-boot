package org.example.mangalistspringboot.infra.persistence;

import jakarta.persistence.*;
import org.example.mangalistspringboot.domain.entities.MangaStatus;

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

  @Column
  private MangaStatus status = MangaStatus.PUBLISHING;

  @Column(nullable = false)
  private Double currentChapter;

  @Column
  private Double finalChapter;

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

  @Column
  private Instant updatedAt = Instant.now();

  public MangaJpaEntity(
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

  public MangaJpaEntity() {
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

  public MangaStatus getStatus() {
    return status;
  }

  public void setStatus(final MangaStatus status) {
    this.status = status;
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

  public Double getEnglishChapter() {
    return englishChapter;
  }

  public void setEnglishChapter(final Double englishChapter) {
    this.englishChapter = englishChapter;
  }

  public Double getPortugueseChapter() {
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

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(final Instant updatedAt) {
    this.updatedAt = updatedAt;
  }
}
