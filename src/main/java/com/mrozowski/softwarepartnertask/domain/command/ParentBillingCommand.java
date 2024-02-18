package com.mrozowski.softwarepartnertask.domain.command;

import java.time.OffsetDateTime;

public record ParentBillingCommand(long parentId, OffsetDateTime startDate, OffsetDateTime endDate) {
}
