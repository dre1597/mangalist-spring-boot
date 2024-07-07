package org.example.mangalistspringboot.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.mangalistspringboot.domain.entities.MangaStatus;

import java.time.Instant;
import java.util.UUID;

public record MangaResponse(
    @JsonProperty("id") UUID id,
    @JsonProperty("name") String name,
    @JsonProperty("currentChapter") Double currentChapter,
    @JsonProperty("finalChapter") Double finalChapter,
    @JsonProperty("status") MangaStatus status,
    @JsonProperty("englishChapter") Double englishChapter,
    @JsonProperty("portugueseChapter") Double portugueseChapter,
    @JsonProperty("extraInfo") String extraInfo,
    @JsonProperty("alternativeName") String alternativeName,
    @JsonProperty("createdAt") Instant createdAt
) {
}
