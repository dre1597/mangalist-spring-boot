package org.example.mangalistspringboot.domain.entities;

import jakarta.persistence.*;
import org.example.mangalistspringboot.api.dto.responses.ListMangasResponse;

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
  private double currentChapter;
  @Column
  private double finalChapter;
  @Column()
  private MangaStatus status = MangaStatus.PUBLISHING;
  @Column
  private double englishChapter;
  @Column
  private double portugueseChapter;
  @Column
  private String extraInfo;
  @Column(name = "created_at", nullable = false)
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

  public double getCurrentChapter() {
    return currentChapter;
  }

  public void setCurrentChapter(final double currentChapter) {
    this.currentChapter = currentChapter;
  }

  public double getFinalChapter() {
    return finalChapter;
  }

  public void setFinalChapter(final double finalChapter) {
    this.finalChapter = finalChapter;
  }

  public MangaStatus getStatus() {
    return status;
  }

  public void setStatus(final MangaStatus status) {
    this.status = status;
  }

  public double getEnglishChapter() {
    return englishChapter;
  }

  public void setEnglishChapter(final double englishChapter) {
    this.englishChapter = englishChapter;
  }

  public double getPortugueseChapter() {
    return portugueseChapter;
  }

  public void setPortugueseChapter(final double portugueseChapter) {
    this.portugueseChapter = portugueseChapter;
  }

  public String getExtraInfo() {
    return extraInfo;
  }

  public void setExtraInfo(final String extraInfo) {
    this.extraInfo = extraInfo;
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
        this.createdAt
    );
  }
}
