package org.example.mangalistspringboot.domain.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
}
