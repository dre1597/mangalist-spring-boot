package org.example.mangalistspringboot.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.mangalistspringboot.api.dto.requests.CreateMangaRequest;
import org.example.mangalistspringboot.api.dto.responses.ListMangasResponse;
import org.example.mangalistspringboot.domain.helpers.Pagination;
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
  Pagination<ListMangasResponse> list(
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
  void add(@RequestBody final CreateMangaRequest request);
}
