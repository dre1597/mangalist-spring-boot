package org.example.mangalistspringboot.view;

import org.example.mangalistspringboot.domain.entities.MangaStatus;
import org.example.mangalistspringboot.domain.helpers.Pagination;
import org.example.mangalistspringboot.domain.helpers.SearchQuery;
import org.example.mangalistspringboot.infra.api.dto.responses.MangaResponse;
import org.example.mangalistspringboot.infra.api.exceptions.CustomExceptionHandler;
import org.example.mangalistspringboot.usecases.DeleteMangaUseCase;
import org.example.mangalistspringboot.usecases.GetOneMangaUseCase;
import org.example.mangalistspringboot.usecases.ListMangasUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ViewControllerTest {
  private MockMvc mockMvc;

  @Mock
  private ListMangasUseCase listMangasUseCase;

  @Mock
  private GetOneMangaUseCase getOneMangaUseCase;

  @Mock
  private DeleteMangaUseCase deleteMangaUseCase;

  @InjectMocks
  private ViewController viewController;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(viewController)
        .setControllerAdvice(new CustomExceptionHandler())
        .build();
  }

  @Test
  void shouldListMangas() throws Exception {
    var searchQuery = new SearchQuery(0, 10, "", "name", "asc");
    when(listMangasUseCase.execute(searchQuery)).thenReturn(new Pagination<>(0, 10, 1, List.of()));

    mockMvc.perform(get("/")
            .param("page", "0")
            .param("perPage", "10")
            .param("sort", "name")
            .param("dir", "asc"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("mangas"));

    verify(listMangasUseCase, times(1)).execute(searchQuery);
  }

  @Test
  void shouldListMangasWithSearchTerms() throws Exception {
    var searchQuery = new SearchQuery(0, 10, "test", "name", "asc");
    when(listMangasUseCase.execute(searchQuery)).thenReturn(new Pagination<>(0, 10, 1, List.of()));

    mockMvc.perform(get("/")
            .param("page", "0")
            .param("perPage", "10")
            .param("terms", "test")
            .param("sort", "name")
            .param("dir", "asc"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("mangas"));

    verify(listMangasUseCase, times(1)).execute(searchQuery);
  }

  @Test
  void shouldReturnCreateView() throws Exception {
    mockMvc.perform(get("/create/"))
        .andExpect(status().isOk())
        .andExpect(view().name("create"));
  }

  @Test
  void shouldReturnEditViewWithMangaData() throws Exception {
    var id = UUID.randomUUID();
    var mangaResponse = new MangaResponse(
        id, "manga_name", MangaStatus.PUBLISHING, 1.0, 10.0, 10.0, 10.0,
        "extra_info", "alternative_name", Instant.now(), Instant.now()
    );

    when(getOneMangaUseCase.execute(id)).thenReturn(mangaResponse);

    mockMvc.perform(get("/edit/{id}", id))
        .andExpect(status().isOk())
        .andExpect(view().name("create"))
        .andExpect(model().attributeExists("manga"));

    verify(getOneMangaUseCase, times(1)).execute(id);
  }

  @Test
  void shouldReturnNotFoundWhenEditingNonExistentManga() throws Exception {
    var id = UUID.randomUUID();
    when(getOneMangaUseCase.execute(id)).thenThrow(new NoSuchElementException("Manga not found"));

    mockMvc.perform(get("/edit/{id}", id))
        .andExpect(status().isNotFound());

    verify(getOneMangaUseCase, times(1)).execute(id);
  }

  @Test
  void shouldDeleteMangaAndRedirect() throws Exception {
    var id = UUID.randomUUID();

    mockMvc.perform(get("/delete/{id}", id))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"));

    verify(deleteMangaUseCase, times(1)).execute(id);
  }

  @Test
  void shouldReturnNotFoundWhenDeletingNonExistentManga() throws Exception {
    var id = UUID.randomUUID();
    doThrow(new NoSuchElementException("Manga not found"))
        .when(deleteMangaUseCase).execute(id);

    mockMvc.perform(get("/delete/{id}", id))
        .andExpect(status().isNotFound());

    verify(deleteMangaUseCase, times(1)).execute(id);
  }
}
