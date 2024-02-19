package com.mrozowski.softwarepartnertask.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record ParentBilling(long id,
                            String firstname,
                            String lastname,
                            long totalCostInCents,
                            List<ChildBilling> children) {

  @Builder
  public record ChildBilling(
      long id, String firstname, String lastname, long totalCostInCents, int totalSpendHours, int payedHours) {
  }
}
