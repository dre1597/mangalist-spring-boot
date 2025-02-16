package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.domain.entities.MangaStatus;
import org.example.mangalistspringboot.infra.api.dto.responses.MangaResponse;
import org.example.mangalistspringboot.infra.persistence.MangaJpaEntity;
import org.example.mangalistspringboot.infra.persistence.MangaJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOneMangaUseCaseTest {
  @InjectMocks
  private GetOneMangaUseCase getOneMangaUseCase;

  @Mock
  private MangaJpaRepository mangaJpaRepository;

  @Test
  void shouldReturnMangaResponseWhenMangaExists() {
    var id = UUID.randomUUID();
    Instant now = Instant.now();

    var mangaJpaEntity = new MangaJpaEntity(
        id,
        "any_manga",
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

    when(mangaJpaRepository.findById(id)).thenReturn(Optional.of(mangaJpaEntity));

    MangaResponse response = getOneMangaUseCase.execute(id);

    assertEquals(id, response.id());
    assertEquals("any_manga", response.name());
    assertEquals(MangaStatus.PUBLISHING, response.status());
    assertEquals(1.0, response.currentChapter());
    assertEquals(10.0, response.finalChapter());
    assertEquals(10.0, response.englishChapter());
    assertEquals(10.0, response.portugueseChapter());
    assertEquals("any_extra_info", response.extraInfo());
    assertEquals("any_alternative_name", response.alternativeName());
    assertEquals(now, response.createdAt());
    assertEquals(now, response.updatedAt());
  }

  @Test
  void shouldThrowExceptionWhenMangaDoesNotExist() {
    var id = UUID.randomUUID();
    when(mangaJpaRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> getOneMangaUseCase.execute(id));
  }
}
