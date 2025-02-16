package org.example.mangalistspringboot.usecases;

import org.example.mangalistspringboot.domain.entities.MangaStatus;
import org.example.mangalistspringboot.domain.helpers.SearchQuery;
import org.example.mangalistspringboot.infra.persistence.MangaJpaEntity;
import org.example.mangalistspringboot.infra.persistence.MangaJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.data.jpa.domain.Specification.where;

@ExtendWith(MockitoExtension.class)
class ListMangasUseCaseTest {
  @InjectMocks
  private ListMangasUseCase listMangasUseCase;

  @Mock
  private MangaJpaRepository mangaJpaRepository;

  @Test
  void shouldReturnPaginatedMangasWithoutSearchTerms() {
    var searchQuery = new SearchQuery(0, 10, " ", "name", "ASC");
    var pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
    var mangaJpaEntities = List.of(
        new MangaJpaEntity(
            UUID.randomUUID(),
            "manga_1",
            MangaStatus.PUBLISHING,
            1.0,
            10.0,
            10.0,
            10.0,
            "extra_info_1",
            "alternative_name_1",
            Instant.now(),
            Instant.now()
        ),
        new MangaJpaEntity(
            UUID.randomUUID(),
            "manga_2",
            MangaStatus.FINISHED,
            2.0,
            20.0,
            20.0,
            20.0,
            "extra_info_2",
            "alternative_name_2",
            Instant.now(),
            Instant.now()
        )
    );
    var page = new PageImpl<>(mangaJpaEntities, pageRequest, mangaJpaEntities.size());

    when(mangaJpaRepository.findAll(where(null), pageRequest)).thenReturn(page);

    var result = listMangasUseCase.execute(searchQuery);

    assertEquals(0, result.currentPage());
    assertEquals(10, result.perPage());
    assertEquals(2, result.total());
    assertEquals(2, result.items().size());
    assertEquals("manga_1", result.items().get(0).name());
    assertEquals("manga_2", result.items().get(1).name());
  }

  @Test
  void shouldReturnPaginatedMangasWithSearchTerms() {
    var searchQuery = new SearchQuery(0, 10, "manga", "name", "ASC");
    var pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
    var mangaJpaEntities = List.of(
        new MangaJpaEntity(
            UUID.randomUUID(),
            "manga_1",
            MangaStatus.PUBLISHING,
            1.0,
            10.0,
            10.0,
            10.0,
            "extra_info_1",
            "alternative_name_1",
            Instant.now(),
            Instant.now()
        )
    );
    var page = new PageImpl<>(mangaJpaEntities, pageRequest, mangaJpaEntities.size());

    when(mangaJpaRepository.findAll(any(Specification.class), eq(pageRequest))).thenReturn(page);

    var result = listMangasUseCase.execute(searchQuery);

    assertEquals(0, result.currentPage());
    assertEquals(10, result.perPage());
    assertEquals(1, result.total());
    assertEquals(1, result.items().size());
    assertEquals("manga_1", result.items().get(0).name());
  }


  @Test
  void shouldReturnEmptyPaginatedMangasWhenNoResults() {
    var searchQuery = new SearchQuery(0, 10, "nonexistent", "name", "ASC");
    var pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
    var page = new PageImpl<>(List.of(), pageRequest, 0);

    when(mangaJpaRepository.findAll(any(Specification.class), eq(pageRequest))).thenReturn(page);

    var result = listMangasUseCase.execute(searchQuery);

    assertEquals(0, result.currentPage());
    assertEquals(10, result.perPage());
    assertEquals(0, result.total());
    assertEquals(0, result.items().size());
  }
}
