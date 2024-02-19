package com.mrozowski.softwarepartnertask.domain.exception;

import java.io.Serial;

public class ParentNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 5806476230272002197L;
  private static final String defaultMessage = "Parent with id: %d not found";

  public ParentNotFoundException(long parentId) {
    super(defaultMessage.formatted(parentId));
  }
}
