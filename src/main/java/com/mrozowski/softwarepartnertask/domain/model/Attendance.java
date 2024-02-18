package com.mrozowski.softwarepartnertask.domain.model;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record Attendance(long id, long childId, OffsetDateTime entryDate, OffsetDateTime exitDate) {
}
