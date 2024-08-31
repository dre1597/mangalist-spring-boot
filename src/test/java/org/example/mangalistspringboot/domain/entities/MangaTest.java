package org.example.mangalistspringboot.domain.entities;

import org.example.mangalistspringboot.infra.api.dto.requests.UpdateMangaRequest;
import org.example.mangalistspringboot.infra.persistence.MangaJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MangaTest {
  @Test
  void shouldCreateMangaWithAllFields() {
    final var manga = Manga.newManga(
        "Manga",
        MangaStatus.PUBLISHING,
        1.0,
        10.0,
        10.0,
        10.0,
        "Extra info",
        "Alternative name"
    );

    assertEquals("Manga", manga.getName());
    assertEquals(MangaStatus.PUBLISHING, manga.getStatus());
    assertEquals(1.0, manga.getCurrentChapter());
    assertEquals(10.0, manga.getFinalChapter());
    assertEquals(10.0, manga.getEnglishChapter());
    assertEquals(10.0, manga.getPortugueseChapter());
    assertEquals("Extra info", manga.getExtraInfo());
    assertEquals("Alternative name", manga.getAlternativeName());
    assertNotNull(manga.getCreatedAt());
    assertNotNull(manga.getUpdatedAt());
  }

  @Test
  void shouldCreateMangaWithoutAllFields() {
    final var manga = Manga.newManga(
        "Manga",
        MangaStatus.PUBLISHING,
        1.0,
        null,
        null,
        null,
        null,
        null
    );

    assertEquals("Manga", manga.getName());
    assertEquals(MangaStatus.PUBLISHING, manga.getStatus());
    assertEquals(1.0, manga.getCurrentChapter());
    assertNull(manga.getFinalChapter());
    assertNull(manga.getEnglishChapter());
    assertNull(manga.getPortugueseChapter());
    assertNull(manga.getExtraInfo());
    assertNull(manga.getAlternativeName());
    assertNotNull(manga.getCreatedAt());
    assertNotNull(manga.getUpdatedAt());
  }

  @ParameterizedTest
  @CsvSource({
      "'null', 'Name cannot be null or empty'",
      "'', 'Name cannot be null or empty'",
  })
  void shouldNotCreateMangaWithInvalidName(final String name, final String expectedMessage) {
    final var exception = assertThrows(
        IllegalArgumentException.class,
        () -> Manga.newManga(
            "null".equals(name) ? null : name,
            MangaStatus.PUBLISHING,
            1.0,
            null,
            null,
            null,
            null,
            null
        )
    );

    assertEquals(expectedMessage, exception.getMessage());
  }

  @ParameterizedTest
  @CsvSource({
      "'null', 'Current chapter cannot be null, zero or negative'",
      "'0.0', 'Current chapter cannot be null, zero or negative'",
      "'-1.0', 'Current chapter cannot be null, zero or negative'"
  })
  void shouldNotCreateMangaWithInvalidCurrentChapter(final String currentChapter, final String expectedMessage) {
    final var exception = assertThrows(
        IllegalArgumentException.class,
        () -> Manga.newManga(
            "Manga",
            MangaStatus.PUBLISHING,
            "null".equals(currentChapter) ? null : Double.valueOf(currentChapter),
            null,
            null,
            null,
            null,
            null
        )
    );

    assertEquals(expectedMessage, exception.getMessage());
  }

  @ParameterizedTest
  @CsvSource({
      "'0.0', 'Final chapter cannot be zero or negative'",
      "'-1.0', 'Final chapter cannot be zero or negative'"
  })
  void shouldNotCreateMangaWithInvalidFinalChapter(final String finalChapter, final String expectedMessage) {
    final var exception = assertThrows(
        IllegalArgumentException.class,
        () -> Manga.newManga(
            "Manga",
            MangaStatus.PUBLISHING,
            1.0,
            "null".equals(finalChapter) ? null : Double.valueOf(finalChapter),
            null,
            null,
            null,
            null
        )
    );

    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  void shouldNotCreateMangaWithCurrentChapterGreaterThanFinalChapter() {
    final var exception = assertThrows(
        IllegalArgumentException.class,
        () -> Manga.newManga(
            "Manga",
            MangaStatus.PUBLISHING,
            2.0,
            1.0,
            null,
            null,
            null,
            null
        )
    );

    assertEquals("Current chapter cannot be greater than final chapter", exception.getMessage());
  }

  @ParameterizedTest
  @CsvSource({
      "'0.0', 'English chapter cannot be zero or negative'",
      "'-1.0', 'English chapter cannot be zero or negative'"
  })
  void shouldNotCreateMangaWithInvalidEnglishChapter(final String englishChapter, final String expectedMessage) {
    final var exception = assertThrows(
        IllegalArgumentException.class,
        () -> Manga.newManga(
            "Manga",
            MangaStatus.PUBLISHING,
            1.0,
            null,
            "null".equals(englishChapter) ? null : Double.valueOf(englishChapter),
            null,
            null,
            null
        )
    );

    assertEquals(expectedMessage, exception.getMessage());
  }

  @ParameterizedTest
  @CsvSource({
      "'0.0', 'Portuguese chapter cannot be zero or negative'",
      "'-1.0', 'Portuguese chapter cannot be zero or negative'"
  })
  void shouldNotCreateMangaWithInvalidPortugueseChapter(final String portugueseChapter, final String expectedMessage) {
    final var exception = assertThrows(
        IllegalArgumentException.class,
        () -> Manga.newManga(
            "Manga",
            MangaStatus.PUBLISHING,
            1.0,
            null,
            null,
            "null".equals(portugueseChapter) ? null : Double.valueOf(portugueseChapter),
            null,
            null
        )
    );

    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  void shouldUpdateMangaWithAllFields() {
    final var manga = this.createManga();
    final var dto = new UpdateMangaRequest(
        "updatedManga",
        MangaStatus.FINISHED,
        100.0,
        200.0,
        200.0,
        200.0,
        "Updated extra info",
        "Updated alternative name"
    );
    var updatedManga = manga.update(dto);
    assertEquals(dto.name(), manga.getName());
    assertEquals(dto.status(), manga.getStatus());
    assertEquals(dto.currentChapter(), manga.getCurrentChapter());
    assertEquals(dto.finalChapter(), manga.getFinalChapter());
    assertEquals(dto.englishChapter(), manga.getEnglishChapter());
    assertEquals(dto.portugueseChapter(), manga.getPortugueseChapter());
    assertEquals(dto.extraInfo(), manga.getExtraInfo());
    assertEquals(dto.alternativeName(), manga.getAlternativeName());

    assertEquals(dto.name(), updatedManga.getName());
    assertEquals(dto.status(), updatedManga.getStatus());
    assertEquals(dto.currentChapter(), updatedManga.getCurrentChapter());
    assertEquals(dto.finalChapter(), updatedManga.getFinalChapter());
    assertEquals(dto.englishChapter(), updatedManga.getEnglishChapter());
    assertEquals(dto.portugueseChapter(), updatedManga.getPortugueseChapter());
    assertEquals(dto.extraInfo(), updatedManga.getExtraInfo());
    assertEquals(dto.alternativeName(), updatedManga.getAlternativeName());
  }

  @ParameterizedTest
  @CsvSource({
      "'null', 'Name cannot be null or empty'",
      "'', 'Name cannot be null or empty'",
  })
  void shouldNotUpdateMangaWithInvalidName(final String name, final String expectedMessage) {
    final var manga = this.createManga();
    final var dto = new UpdateMangaRequest(
        "null".equals(name) ? null : name,
        manga.getStatus(),
        manga.getCurrentChapter(),
        manga.getFinalChapter(),
        manga.getEnglishChapter(),
        manga.getPortugueseChapter(),
        manga.getExtraInfo(),
        manga.getAlternativeName()
    );
    final var exception = assertThrows(
        IllegalArgumentException.class,
        () -> manga.update(dto)
    );

    assertEquals(expectedMessage, exception.getMessage());
  }

  @ParameterizedTest
  @CsvSource({
      "'null', 'Current chapter cannot be null, zero or negative'",
      "'0.0', 'Current chapter cannot be null, zero or negative'",
      "'-1.0', 'Current chapter cannot be null, zero or negative'"
  })
  void shouldNotUpdateMangaWithInvalidCurrentChapter(final String currentChapter, final String expectedMessage) {
    final var manga = this.createManga();
    final var dto = new UpdateMangaRequest(
        manga.getName(),
        manga.getStatus(),
        "null".equals(currentChapter) ? null : Double.valueOf(currentChapter),
        manga.getFinalChapter(),
        manga.getEnglishChapter(),
        manga.getPortugueseChapter(),
        manga.getExtraInfo(),
        manga.getAlternativeName()
    );
    final var exception = assertThrows(
        IllegalArgumentException.class,
        () -> manga.update(dto)
    );

    assertEquals(expectedMessage, exception.getMessage());
  }

  @ParameterizedTest
  @CsvSource({
      "'0.0', 'Final chapter cannot be zero or negative'",
      "'-1.0', 'Final chapter cannot be zero or negative'"
  })
  void shouldNotUpdateMangaWithInvalidFinalChapter(final String finalChapter, final String expectedMessage) {
    final var manga = this.createManga();
    final var dto = new UpdateMangaRequest(
        manga.getName(),
        manga.getStatus(),
        manga.getCurrentChapter(),
        "null".equals(finalChapter) ? null : Double.valueOf(finalChapter),
        manga.getEnglishChapter(),
        manga.getPortugueseChapter(),
        manga.getExtraInfo(),
        manga.getAlternativeName()
    );
    final var exception = assertThrows(
        IllegalArgumentException.class,
        () -> manga.update(dto)
    );

    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  void shouldNotUpdateMangaWithCurrentChapterGreaterThanFinalChapter() {
    final var manga = this.createManga();
    final var dto = new UpdateMangaRequest(
        manga.getName(),
        manga.getStatus(),
        2.0,
        1.0,
        manga.getEnglishChapter(),
        manga.getPortugueseChapter(),
        manga.getExtraInfo(),
        manga.getAlternativeName()
    );

    final var exception = assertThrows(
        IllegalArgumentException.class,
        () -> manga.update(dto)
    );

    assertEquals("Current chapter cannot be greater than final chapter", exception.getMessage());
  }

  @ParameterizedTest
  @CsvSource({
      "'0.0', 'English chapter cannot be zero or negative'",
      "'-1.0', 'English chapter cannot be zero or negative'"
  })
  void shouldNotUpdateMangaWithInvalidEnglishChapter(final String englishChapter, final String expectedMessage) {
    final var manga = this.createManga();
    final var dto = new UpdateMangaRequest(
        manga.getName(),
        manga.getStatus(),
        manga.getCurrentChapter(),
        manga.getFinalChapter(),
        "null".equals(englishChapter) ? null : Double.valueOf(englishChapter),
        manga.getPortugueseChapter(),
        manga.getExtraInfo(),
        manga.getAlternativeName()
    );
    final var exception = assertThrows(
        IllegalArgumentException.class,
        () -> manga.update(dto)
    );

    assertEquals(expectedMessage, exception.getMessage());
  }

  @ParameterizedTest
  @CsvSource({
      "'0.0', 'Portuguese chapter cannot be zero or negative'",
      "'-1.0', 'Portuguese chapter cannot be zero or negative'"
  })
  void shouldNotUpdateMangaWithInvalidPortugueseChapter(final String portugueseChapter, final String expectedMessage) {
    final var manga = this.createManga();
    final var dto = new UpdateMangaRequest(
        manga.getName(),
        manga.getStatus(),
        manga.getCurrentChapter(),
        manga.getFinalChapter(),
        manga.getEnglishChapter(),
        "null".equals(portugueseChapter) ? null : Double.valueOf(portugueseChapter),
        manga.getExtraInfo(),
        manga.getAlternativeName()
    );
    final var exception = assertThrows(
        IllegalArgumentException.class,
        () -> manga.update(dto)
    );

    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  void shouldTransformMangaEntityIntoJpaEntity() {
    final var manga = this.createManga();
    final var mangaJpaEntity = manga.toJpaEntity();
    assertEquals(manga.getId(), mangaJpaEntity.getId());
    assertEquals(manga.getName(), mangaJpaEntity.getName());
    assertEquals(manga.getStatus(), mangaJpaEntity.getStatus());
    assertEquals(manga.getCurrentChapter(), mangaJpaEntity.getCurrentChapter());
    assertEquals(manga.getFinalChapter(), mangaJpaEntity.getFinalChapter());
    assertEquals(manga.getEnglishChapter(), mangaJpaEntity.getEnglishChapter());
    assertEquals(manga.getPortugueseChapter(), mangaJpaEntity.getPortugueseChapter());
    assertEquals(manga.getExtraInfo(), mangaJpaEntity.getExtraInfo());
    assertEquals(manga.getAlternativeName(), mangaJpaEntity.getAlternativeName());
    assertEquals(manga.getCreatedAt(), mangaJpaEntity.getCreatedAt());
    assertEquals(manga.getUpdatedAt(), mangaJpaEntity.getUpdatedAt());
  }

  @Test
  void shouldTransformMangaJpaEntityIntoEntity() {
    final var mangaJpaEntity = this.createMangaJpaEntity();
    final var manga = Manga.fromJpaEntity(mangaJpaEntity);
    assertEquals(manga.getId(), mangaJpaEntity.getId());
    assertEquals(manga.getName(), mangaJpaEntity.getName());
    assertEquals(manga.getStatus(), mangaJpaEntity.getStatus());
    assertEquals(manga.getCurrentChapter(), mangaJpaEntity.getCurrentChapter());
    assertEquals(manga.getFinalChapter(), mangaJpaEntity.getFinalChapter());
    assertEquals(manga.getEnglishChapter(), mangaJpaEntity.getEnglishChapter());
    assertEquals(manga.getPortugueseChapter(), mangaJpaEntity.getPortugueseChapter());
    assertEquals(manga.getExtraInfo(), mangaJpaEntity.getExtraInfo());
    assertEquals(manga.getAlternativeName(), mangaJpaEntity.getAlternativeName());
    assertEquals(manga.getCreatedAt(), mangaJpaEntity.getCreatedAt());
    assertEquals(manga.getUpdatedAt(), mangaJpaEntity.getUpdatedAt());
  }

  @Test
  void shouldTransformMangaIntoMangaResponse() {
    final var manga = this.createManga();
    final var mangaResponse = manga.toResponse();
    assertEquals(manga.getId(), mangaResponse.id());
    assertEquals(manga.getName(), mangaResponse.name());
    assertEquals(manga.getStatus(), mangaResponse.status());
    assertEquals(manga.getCurrentChapter(), mangaResponse.currentChapter());
    assertEquals(manga.getFinalChapter(), mangaResponse.finalChapter());
    assertEquals(manga.getEnglishChapter(), mangaResponse.englishChapter());
    assertEquals(manga.getPortugueseChapter(), mangaResponse.portugueseChapter());
    assertEquals(manga.getExtraInfo(), mangaResponse.extraInfo());
    assertEquals(manga.getAlternativeName(), mangaResponse.alternativeName());
    assertEquals(manga.getCreatedAt(), mangaResponse.createdAt());
    assertEquals(manga.getUpdatedAt(), mangaResponse.updatedAt());
  }

  private Manga createManga() {
    return Manga.newManga(
        "Manga",
        MangaStatus.PUBLISHING,
        1.0,
        10.0,
        10.0,
        10.0,
        "Extra info",
        "Alternative name"
    );
  }

  private MangaJpaEntity createMangaJpaEntity() {
    return new MangaJpaEntity(
        UUID.randomUUID(),
        "Manga",
        MangaStatus.PUBLISHING,
        1.0,
        10.0,
        10.0,
        10.0,
        "Extra info",
        "Alternative name",
        Instant.now(),
        Instant.now()
    );
  }
}
