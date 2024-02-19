package com.mrozowski.softwarepartnertask.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record SchoolBilling(long id,
                            String name,
                            long totalCostInCents,
                            List<ParentBilling> parents) {

}
