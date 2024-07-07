package org.example.mangalistspringboot.api.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.mangalistspringboot.domain.entities.MangaStatus;

import java.util.Optional;

// TODO: create an entity and move validation to it

public record UpdateMangaRequest(
    @JsonProperty("name") Optional<@NotBlank String> name,
    @JsonProperty("currentChapter") Optional<@PositiveOrZero Double> currentChapter,
    @JsonProperty("finalChapter") Optional<@PositiveOrZero Double> finalChapter,
    @JsonProperty("status") Optional<MangaStatus> status,
    @JsonProperty("englishChapter") Optional<@PositiveOrZero Double> englishChapter,
    @JsonProperty("portugueseChapter") Optional<@PositiveOrZero Double> portugueseChapter,
    @JsonProperty("extraInfo") Optional<String> extraInfo,
    @JsonProperty("alternativeName") Optional<String> alternativeName
) {
}
