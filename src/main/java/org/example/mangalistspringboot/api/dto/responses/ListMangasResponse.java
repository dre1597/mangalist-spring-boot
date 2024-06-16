package org.example.mangalistspringboot.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.mangalistspringboot.domain.entities.MangaStatus;

import java.time.Instant;
import java.util.UUID;

public record ListMangasResponse(
    @JsonProperty("id") UUID id,
    @JsonProperty("name") String name,
    @JsonProperty("currentChapter") double currentChapter,
    @JsonProperty("finalChapter") double finalChapter,
    @JsonProperty("status") MangaStatus status,
    @JsonProperty("englishChapter") double englishChapter,
    @JsonProperty("portugueseChapter") double portugueseChapter,
    @JsonProperty("extraInfo") String extraInfo,
    @JsonProperty("createdAt") Instant createdAt
) {
}
