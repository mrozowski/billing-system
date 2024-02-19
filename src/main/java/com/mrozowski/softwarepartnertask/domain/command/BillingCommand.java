package com.mrozowski.softwarepartnertask.domain.command;

import java.time.OffsetDateTime;

public record BillingCommand(long id, OffsetDateTime startDate, OffsetDateTime endDate) {
}
