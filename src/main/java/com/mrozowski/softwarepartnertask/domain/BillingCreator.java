package com.mrozowski.softwarepartnertask.domain;

import com.mrozowski.softwarepartnertask.domain.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
class BillingCreator {

  private final CostCalculatorService costCalculatorService;

  SchoolBilling createSchoolBilling(School school, List<ParentBilling> parentBillingList) {
    return SchoolBilling.builder()
        .id(school.id())
        .name(school.name())
        .parents(parentBillingList)
        .totalCostInCents(getTotalParentsCost(parentBillingList))
        .build();
  }

  ParentBilling createParentBilling(Parent parent, List<Attendance> attendanceList) {
    log.info("Calculating billing for parent with id [{}]", parent.id());
    var childAttendanceMap = attendanceList.stream().collect(Collectors.groupingBy(Attendance::childId));
    var childBillingList = createChildBillingList(parent, childAttendanceMap);

    return ParentBilling.builder()
        .id(parent.id())
        .firstname(parent.firstname())
        .lastname(parent.lastname())
        .children(childBillingList)
        .totalCostInCents(getTotalChildrenCost(childBillingList))
        .build();
  }

  private static long getTotalChildrenCost(List<ParentBilling.ChildBilling> childBillingList) {
    return childBillingList.stream().mapToLong(ParentBilling.ChildBilling::totalCostInCents).sum();
  }

  private static long getTotalParentsCost(List<ParentBilling> parentBillingList) {
    return parentBillingList.stream().mapToLong(ParentBilling::totalCostInCents).sum();
  }

  private List<ParentBilling.ChildBilling> createChildBillingList(
      Parent parent, Map<Long, List<Attendance>> childAttendanceMap) {
    return parent.children().stream()
        .map(e -> createChildBilling(e, childAttendanceMap.getOrDefault(e.id(), List.of())))
        .toList();
  }

  private ParentBilling.ChildBilling createChildBilling(Parent.Child child, List<Attendance> attendanceList) {
    log.info("Calculating billing for child with id [{}]", child.id());
    var result = costCalculatorService.calculate(attendanceList, child.school().hourPriceInCent());
    return ParentBilling.ChildBilling.builder()
        .id(child.id())
        .firstname(child.firstname())
        .lastname(child.lastName())
        .totalCostInCents(result.totalCost())
        .totalSpendHours(result.totalSpendHours())
        .payedHours(result.payedHours())
        .build();
  }
}
