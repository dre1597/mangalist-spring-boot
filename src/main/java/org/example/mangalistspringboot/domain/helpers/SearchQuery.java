package org.example.mangalistspringboot.domain.helpers;

public record SearchQuery(
    int page,
    int perPage,
    String terms,
    String sort,
    String direction
) {
}
