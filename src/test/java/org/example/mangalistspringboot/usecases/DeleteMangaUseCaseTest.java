package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.domain.entities.MangaStatus;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteMangaUseCaseTest {
  @InjectMocks
  private DeleteMangaUseCase deleteMangaUseCase;

  @Mock
  private MangaJpaRepository mangaJpaRepository;

  @Test
  void shouldDeleteMangaWhenMangaExists() {
    var id = UUID.randomUUID();
    var now = Instant.now();

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

    deleteMangaUseCase.execute(id);

    verify(mangaJpaRepository, times(1)).delete(mangaJpaEntity);
  }

  @Test
  void shouldDoNothingWhenMangaDoesNotExist() {
    var id = UUID.randomUUID();
    when(mangaJpaRepository.findById(id)).thenReturn(Optional.empty());

    deleteMangaUseCase.execute(id);

    verify(mangaJpaRepository, never()).delete(any());
  }
}
