package com.mrozowski.softwarepartnertask.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Parent(Long id, String firstname, String lastname, List<Child> children) {

  @Builder
  public static record Child(Long id, String firstname, String lastName, School school){}
}
