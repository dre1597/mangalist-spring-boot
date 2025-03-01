package org.example.mangalistspringboot.infra.api.controllers;

import org.example.mangalistspringboot.domain.entities.MangaStatus;
import org.example.mangalistspringboot.domain.exceptions.MangaAlreadyExistsException;
import org.example.mangalistspringboot.domain.helpers.Pagination;
import org.example.mangalistspringboot.domain.helpers.SearchQuery;
import org.example.mangalistspringboot.infra.api.dto.requests.CreateMangaRequest;
import org.example.mangalistspringboot.infra.api.dto.requests.UpdateMangaRequest;
import org.example.mangalistspringboot.infra.api.dto.responses.MangaResponse;
import org.example.mangalistspringboot.infra.api.exceptions.CustomExceptionHandler;
import org.example.mangalistspringboot.usecases.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MangaControllerTest {
  private MockMvc mockMvc;

  @Mock
  private ListMangasUseCase listMangasUseCase;

  @Mock
  private CreateMangaUseCase createMangaUseCase;

  @Mock
  private GetOneMangaUseCase getOneMangaUseCase;

  @Mock
  private UpdateMangaUseCase updateMangaUseCase;

  @Mock
  private DeleteMangaUseCase deleteMangaUseCase;

  @InjectMocks
  private MangaController mangaController;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(mangaController)
        .setControllerAdvice(new CustomExceptionHandler())
        .build();
  }

  @Test
  void shouldListMangas() throws Exception {
    var searchQuery = new SearchQuery(0, 10, "search", "name", "asc");
    var pagination = new Pagination<MangaResponse>(0, 10, 1, List.of());

    when(listMangasUseCase.execute(searchQuery)).thenReturn(pagination);

    mockMvc.perform(get("/mangas")
            .param("search", "search")
            .param("page", "0")
            .param("perPage", "10")
            .param("sort", "name")
            .param("dir", "asc"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.currentPage").value(0))
        .andExpect(jsonPath("$.perPage").value(10))
        .andExpect(jsonPath("$.total").value(1))
        .andExpect(jsonPath("$.items").isArray());

    verify(listMangasUseCase, times(1)).execute(searchQuery);
  }

  @Test
  void shouldAddManga() throws Exception {
    var request = new CreateMangaRequest(
        "manga_name",
        MangaStatus.PUBLISHING,
        1.0,
        10.0,
        10.0,
        10.0,
        "extra_info",
        "alternative_name"
    );

    mockMvc.perform(post("/mangas")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "name": "manga_name",
                  "status": "PUBLISHING",
                  "currentChapter": 1.0,
                  "finalChapter": 10.0,
                  "englishChapter": 10.0,
                  "portugueseChapter": 10.0,
                  "extraInfo": "extra_info",
                  "alternativeName": "alternative_name"
                }
                """))
        .andExpect(status().isCreated());

    verify(createMangaUseCase, times(1)).execute(request);
  }

  @Test
  void shouldReturnConflictWhenMangaAlreadyExists() throws Exception {
    var request = new CreateMangaRequest(
        "manga_name",
        MangaStatus.PUBLISHING,
        1.0,
        10.0,
        10.0,
        10.0,
        "extra_info",
        "alternative_name"
    );

    doThrow(new MangaAlreadyExistsException("Manga already exists"))
        .when(createMangaUseCase).execute(request);

    mockMvc.perform(post("/mangas")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "name": "manga_name",
                  "status": "PUBLISHING",
                  "currentChapter": 1.0,
                  "finalChapter": 10.0,
                  "englishChapter": 10.0,
                  "portugueseChapter": 10.0,
                  "extraInfo": "extra_info",
                  "alternativeName": "alternative_name"
                }
                """))
        .andExpect(status().isConflict())
        .andExpect(result -> assertEquals("Manga already exists", result.getResolvedException().getMessage()));

    verify(createMangaUseCase, times(1)).execute(request);
  }

  @Test
  void shouldReturnBadRequestWhenInvalidRequestBody() throws Exception {
    mockMvc.perform(post("/mangas")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "name": "",
                  "status": "INVALID_STATUS",
                  "currentChapter": -1.0,
                  "finalChapter": -1.0,
                  "englishChapter": -1.0,
                  "portugueseChapter": -1.0,
                  "extraInfo": "",
                  "alternativeName": ""
                }
                """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnInternalServerErrorWhenUnexpectedErrorOccurs() throws Exception {
    var request = new CreateMangaRequest(
        "manga_name",
        MangaStatus.PUBLISHING,
        1.0,
        10.0,
        10.0,
        10.0,
        "extra_info",
        "alternative_name"
    );

    doThrow(new RuntimeException("Unexpected error"))
        .when(createMangaUseCase).execute(request);

    mockMvc.perform(post("/mangas")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "name": "manga_name",
                  "status": "PUBLISHING",
                  "currentChapter": 1.0,
                  "finalChapter": 10.0,
                  "englishChapter": 10.0,
                  "portugueseChapter": 10.0,
                  "extraInfo": "extra_info",
                  "alternativeName": "alternative_name"
                }
                """))
        .andExpect(status().isInternalServerError());

    verify(createMangaUseCase, times(1)).execute(request);
  }

  @Test
  void shouldFindOneManga() throws Exception {
    var id = UUID.randomUUID();
    var mangaResponse = new MangaResponse(
        id,
        "any_manga_name",
        MangaStatus.PUBLISHING,
        1.0,
        10.0,
        10.0,
        10.0,
        "extra_info",
        "alternative_name",
        Instant.now(),
        Instant.now()
    );

    when(getOneMangaUseCase.execute(id)).thenReturn(mangaResponse);

    mockMvc.perform(get("/mangas/{mangaId}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()))
        .andExpect(jsonPath("$.name").value("any_manga_name"));

    verify(getOneMangaUseCase, times(1)).execute(id);
  }

  @Test
  void shouldUpdateManga() throws Exception {
    var id = UUID.randomUUID();
    var request = new UpdateMangaRequest(
        "any_updated_name",
        MangaStatus.FINISHED,
        2.0,
        20.0,
        20.0,
        20.0,
        "any_updated_extra_info",
        "any_updated_alternative_name"
    );

    mockMvc.perform(patch("/mangas/{mangaId}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "name": "any_updated_name",
                  "status": "FINISHED",
                  "currentChapter": 2.0,
                  "finalChapter": 20.0,
                  "englishChapter": 20.0,
                  "portugueseChapter": 20.0,
                  "extraInfo": "any_updated_extra_info",
                  "alternativeName": "any_updated_alternative_name"
                }
                """))
        .andExpect(status().isOk());

    verify(updateMangaUseCase, times(1)).execute(id, request);
  }

  @Test
  void shouldDeleteManga() throws Exception {
    var id = UUID.randomUUID();

    mockMvc.perform(delete("/mangas/{mangaId}", id))
        .andExpect(status().isNoContent());

    verify(deleteMangaUseCase, times(1)).execute(id);
  }
}
