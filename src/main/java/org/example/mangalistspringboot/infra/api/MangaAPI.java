package org.example.mangalistspringboot.infra.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.mangalistspringboot.domain.helpers.Pagination;
import org.example.mangalistspringboot.infra.api.dto.requests.CreateMangaRequest;
import org.example.mangalistspringboot.infra.api.dto.requests.UpdateMangaRequest;
import org.example.mangalistspringboot.infra.api.dto.responses.MangaResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/mangas")
@Tag(name = "Mangas")
public interface MangaAPI {
  @GetMapping
  @Operation(summary = "List all mangas paginated")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Listed successfully"),
      @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
  })
  Pagination<MangaResponse> list(
      @RequestParam(name = "search", required = false, defaultValue = "") final String search,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
      @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
      @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
  );

  @PostMapping
  @Operation(summary = "Add a new manga")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created successfully"),
      @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
  })
  void add(@Valid @RequestBody final CreateMangaRequest request);

  @GetMapping(path = "/{mangaId}")
  @Operation(summary = "Get one manga")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Not found"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
  })
  MangaResponse findOne(@PathVariable("mangaId") String id);

  @PatchMapping(path = "/{mangaId}")
  @Operation(summary = "Update a manga")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Updated successfully"),
      @ApiResponse(responseCode = "404", description = "Not found"),
      @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
  })
  void update(@PathVariable("mangaId") String id, @Valid @RequestBody final UpdateMangaRequest request);

  @DeleteMapping(path = "/{mangaId}")
  @Operation(summary = "Delete a manga")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Not found"),
      @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
  })
  void delete(@PathVariable("mangaId") String id);
}
