package org.example.mangalistspringboot.infra.api.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.mangalistspringboot.domain.entities.MangaStatus;

import java.util.Optional;

public record UpdateMangaRequest(
    @JsonProperty("name") Optional<String> name,
    @JsonProperty("status") Optional<MangaStatus> status,
    @JsonProperty("currentChapter") Optional<Double> currentChapter,
    @JsonProperty("finalChapter") Optional<Double> finalChapter,
    @JsonProperty("englishChapter") Optional<Double> englishChapter,
    @JsonProperty("portugueseChapter") Optional<Double> portugueseChapter,
    @JsonProperty("extraInfo") Optional<String> extraInfo,
    @JsonProperty("alternativeName") Optional<String> alternativeName
) {
}
