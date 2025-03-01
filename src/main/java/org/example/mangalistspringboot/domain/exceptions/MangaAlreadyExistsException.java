package org.example.mangalistspringboot.domain.exceptions;

public class MangaAlreadyExistsException extends RuntimeException {
  public MangaAlreadyExistsException(final String errorMessage) {
    super(errorMessage);
  }

  public MangaAlreadyExistsException(final String errorMessage, final Throwable err) {
    super(errorMessage, err);
  }
}
