package com.mrozowski.softwarepartnertask.domain.exception;

import java.io.Serial;

public class SchoolNotFoundException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1059534898735000422L;

  private static final String defaultMessage = "School with id: [%d] not found";

  public SchoolNotFoundException(long id) {
    super(defaultMessage.formatted(id));
  }
}
