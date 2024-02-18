package com.mrozowski.softwarepartnertask.domain.model;


import java.time.OffsetDateTime;

public record Billing<T>(OffsetDateTime startDate, OffsetDateTime endDate, T billingSubject) {

}
