package com.mrozowski.softwarepartnertask.domain.model;

import lombok.Builder;

@Builder
public record CalculationResult(int totalSpendHours, int payedHours, int totalCost) {
}
