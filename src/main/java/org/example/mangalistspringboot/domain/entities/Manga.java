package org.example.mangalistspringboot.domain.entities;

import jakarta.persistence.*;
import org.example.mangalistspringboot.api.dto.responses.ListMangasResponse;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Entity(name = "mangas")
@Table()
public class Manga {
  @Id()
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Column(nullable = false, unique = true)
  private String name;
  @Column(nullable = false)
  private Double currentChapter;
  @Column()
  private Double finalChapter;
  @Column()
  private MangaStatus status = MangaStatus.PUBLISHING;
  @Column
  private Double englishChapter;
  @Column
  private Double portugueseChapter;
  @Column
  private String extraInfo;
  @Column
  private String alternativeName;
  @CreatedDate()
  private Instant createdAt;

  public Manga(
      final UUID id,
      final String name,
      final double currentChapter,
      final double finalChapter,
      final MangaStatus status,
      final double englishChapter,
      final double portugueseChapter,
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

  public Manga() {
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

  public ListMangasResponse toResponse() {
    return new ListMangasResponse(
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
