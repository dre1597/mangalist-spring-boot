package org.example.mangalistspringboot.api.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.mangalistspringboot.domain.entities.Manga;
import org.example.mangalistspringboot.domain.entities.MangaStatus;

// TODO: create an entity and move validation to it

public record CreateMangaRequest(
    @JsonProperty("name") @NotNull @NotBlank String name,
    @JsonProperty("currentChapter") @NotNull @PositiveOrZero Double currentChapter,
    @JsonProperty("finalChapter") @PositiveOrZero Double finalChapter,
    @JsonProperty("status") MangaStatus status,
    @JsonProperty("englishChapter") @PositiveOrZero Double englishChapter,
    @JsonProperty("portugueseChapter") @PositiveOrZero Double portugueseChapter,
    @JsonProperty("extraInfo") String extraInfo,
    @JsonProperty("alternativeName") String alternativeName
) {
  public Manga toEntity() {
    return new Manga(
        name,
        currentChapter,
        finalChapter,
        status,
        englishChapter,
        portugueseChapter,
        extraInfo,
        alternativeName
    );
  }
}
