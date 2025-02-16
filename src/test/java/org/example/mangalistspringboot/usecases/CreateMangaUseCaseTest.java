package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.domain.entities.MangaStatus;
import org.example.mangalistspringboot.infra.api.dto.requests.CreateMangaRequest;
import org.example.mangalistspringboot.infra.persistence.MangaJpaEntity;
import org.example.mangalistspringboot.infra.persistence.MangaJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateMangaUseCaseTest {
  @InjectMocks
  private CreateMangaUseCase createMangaUseCase;

  @Mock
  private MangaJpaRepository mangaJpaRepository;

  @Test
  void shouldCreateAManga() {
    var request = new CreateMangaRequest(
        "any_manga",
        MangaStatus.PUBLISHING,
        1.0,
        10.0,
        10.0,
        10.0,
        "any_extra_info",
        "any_alternative_name"
    );

    createMangaUseCase.execute(request);

    ArgumentCaptor<MangaJpaEntity> mangaJpaEntityCaptor = ArgumentCaptor.forClass(MangaJpaEntity.class);
    verify(mangaJpaRepository, times(1)).save(mangaJpaEntityCaptor.capture());

    MangaJpaEntity capturedEntity = mangaJpaEntityCaptor.getValue();
    assertEquals("any_manga", capturedEntity.getName());
    assertEquals(MangaStatus.PUBLISHING, capturedEntity.getStatus());
    assertEquals(1.0, capturedEntity.getCurrentChapter());
    assertEquals(10.0, capturedEntity.getFinalChapter());
    assertEquals(10.0, capturedEntity.getEnglishChapter());
    assertEquals(10.0, capturedEntity.getPortugueseChapter());
    assertEquals("any_extra_info", capturedEntity.getExtraInfo());
    assertEquals("any_alternative_name", capturedEntity.getAlternativeName());
  }
}
