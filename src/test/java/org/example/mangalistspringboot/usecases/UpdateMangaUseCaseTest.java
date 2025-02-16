package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.domain.entities.MangaStatus;
import org.example.mangalistspringboot.infra.api.dto.requests.UpdateMangaRequest;
import org.example.mangalistspringboot.infra.persistence.MangaJpaEntity;
import org.example.mangalistspringboot.infra.persistence.MangaJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateMangaUseCaseTest {
  @InjectMocks
  private UpdateMangaUseCase updateMangaUseCase;

  @Mock
  private MangaJpaRepository mangaJpaRepository;

  @Test
  void shouldUpdateMangaWhenMangaExists() {
    var id = UUID.randomUUID();
    var now = Instant.now();

    var mangaJpaEntity = new MangaJpaEntity(
        id,
        "old_name",
        MangaStatus.PUBLISHING,
        1.0,
        10.0,
        10.0,
        10.0,
        "old_extra_info",
        "old_alternative_name",
        now,
        now
    );

    var request = new UpdateMangaRequest(
        "new_name",
        MangaStatus.FINISHED,
        2.0,
        20.0,
        20.0,
        20.0,
        "new_extra_info",
        "new_alternative_name"
    );

    when(mangaJpaRepository.findById(id)).thenReturn(Optional.of(mangaJpaEntity));

    updateMangaUseCase.execute(id, request);

    ArgumentCaptor<MangaJpaEntity> mangaJpaEntityCaptor = ArgumentCaptor.forClass(MangaJpaEntity.class);
    verify(mangaJpaRepository, times(1)).save(mangaJpaEntityCaptor.capture());

    var savedEntity = mangaJpaEntityCaptor.getValue();
    assertEquals("new_name", savedEntity.getName());
    assertEquals(MangaStatus.FINISHED, savedEntity.getStatus());
    assertEquals(2.0, savedEntity.getCurrentChapter());
    assertEquals(20.0, savedEntity.getFinalChapter());
    assertEquals(20.0, savedEntity.getEnglishChapter());
    assertEquals(20.0, savedEntity.getPortugueseChapter());
    assertEquals("new_extra_info", savedEntity.getExtraInfo());
    assertEquals("new_alternative_name", savedEntity.getAlternativeName());
  }

  @Test
  void shouldThrowExceptionWhenMangaDoesNotExist() {
    UUID id = UUID.randomUUID();
    var request = new UpdateMangaRequest(
        "new_name",
        MangaStatus.FINISHED,
        2.0,
        20.0,
        20.0,
        20.0,
        "new_extra_info",
        "new_alternative_name"
    );

    when(mangaJpaRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> updateMangaUseCase.execute(id, request));
    verify(mangaJpaRepository, never()).save(any());
  }
}
