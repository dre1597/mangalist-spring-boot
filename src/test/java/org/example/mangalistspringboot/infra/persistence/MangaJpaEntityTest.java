package org.example.mangalistspringboot.infra.persistence;

import org.example.mangalistspringboot.domain.entities.MangaStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MangaJpaEntityTest {
  @Test
  void shouldCreateMangaJpaEntityWithAllFields() {
    var id = UUID.randomUUID();
    var now = Instant.now();

    var mangaJpaEntity = new MangaJpaEntity(
        id,
        "any_name",
        MangaStatus.PUBLISHING,
        1.0,
        10.0,
        10.0,
        10.0,
        "any_extra_info",
        "any_alternative_name",
        now,
        now
    );

    assertEquals(id, mangaJpaEntity.getId());
    assertEquals("any_name", mangaJpaEntity.getName());
    assertEquals(MangaStatus.PUBLISHING, mangaJpaEntity.getStatus());
    assertEquals(1.0, mangaJpaEntity.getCurrentChapter());
    assertEquals(10.0, mangaJpaEntity.getFinalChapter());
    assertEquals(10.0, mangaJpaEntity.getEnglishChapter());
    assertEquals(10.0, mangaJpaEntity.getPortugueseChapter());
    assertEquals("any_extra_info", mangaJpaEntity.getExtraInfo());
    assertEquals("any_alternative_name", mangaJpaEntity.getAlternativeName());
    assertEquals(now, mangaJpaEntity.getCreatedAt());
    assertEquals(now, mangaJpaEntity.getUpdatedAt());
  }

  @Test
  void shouldCreateMangaJpaEntityWithDefaultValues() {
    var mangaJpaEntity = new MangaJpaEntity();

    assertNull(mangaJpaEntity.getId());
    assertNull(mangaJpaEntity.getName());
    assertEquals(MangaStatus.PUBLISHING, mangaJpaEntity.getStatus());
    assertNull(mangaJpaEntity.getCurrentChapter());
    assertNull(mangaJpaEntity.getFinalChapter());
    assertNull(mangaJpaEntity.getEnglishChapter());
    assertNull(mangaJpaEntity.getPortugueseChapter());
    assertNull(mangaJpaEntity.getExtraInfo());
    assertNull(mangaJpaEntity.getAlternativeName());
    assertNotNull(mangaJpaEntity.getCreatedAt());
    assertNotNull(mangaJpaEntity.getUpdatedAt());
  }

  @Test
  void shouldSetAndGetFieldsCorrectly() {
    var mangaJpaEntity = new MangaJpaEntity();

    var id = UUID.randomUUID();
    var now = Instant.now();

    mangaJpaEntity.setId(id);
    mangaJpaEntity.setName("any_name");
    mangaJpaEntity.setStatus(MangaStatus.FINISHED);
    mangaJpaEntity.setCurrentChapter(1.0);
    mangaJpaEntity.setFinalChapter(10.0);
    mangaJpaEntity.setEnglishChapter(10.0);
    mangaJpaEntity.setPortugueseChapter(10.0);
    mangaJpaEntity.setExtraInfo("any_extra_info");
    mangaJpaEntity.setAlternativeName("any_alternative_name");
    mangaJpaEntity.setCreatedAt(now);
    mangaJpaEntity.setUpdatedAt(now);

    assertEquals(id, mangaJpaEntity.getId());
    assertEquals("any_name", mangaJpaEntity.getName());
    assertEquals(MangaStatus.FINISHED, mangaJpaEntity.getStatus());
    assertEquals(1.0, mangaJpaEntity.getCurrentChapter());
    assertEquals(10.0, mangaJpaEntity.getFinalChapter());
    assertEquals(10.0, mangaJpaEntity.getEnglishChapter());
    assertEquals(10.0, mangaJpaEntity.getPortugueseChapter());
    assertEquals("any_extra_info", mangaJpaEntity.getExtraInfo());
    assertEquals("any_alternative_name", mangaJpaEntity.getAlternativeName());
    assertEquals(now, mangaJpaEntity.getCreatedAt());
    assertEquals(now, mangaJpaEntity.getUpdatedAt());
  }
}
