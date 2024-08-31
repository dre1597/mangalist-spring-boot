package org.example.mangalistspringboot.infra.api.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.mangalistspringboot.domain.entities.MangaStatus;

public record UpdateMangaRequest(
    @JsonProperty("name") String name,
    @JsonProperty("status") MangaStatus status,
    @JsonProperty("currentChapter") Double currentChapter,
    @JsonProperty("finalChapter") Double finalChapter,
    @JsonProperty("englishChapter") Double englishChapter,
    @JsonProperty("portugueseChapter") Double portugueseChapter,
    @JsonProperty("extraInfo") String extraInfo,
    @JsonProperty("alternativeName") String alternativeName
) {
}
