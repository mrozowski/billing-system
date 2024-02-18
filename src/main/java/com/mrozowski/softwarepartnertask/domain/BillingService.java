package com.mrozowski.softwarepartnertask.domain;

import com.mrozowski.softwarepartnertask.domain.command.ParentBillingCommand;
import com.mrozowski.softwarepartnertask.domain.exception.ParentNotFoundException;
import com.mrozowski.softwarepartnertask.domain.model.Attendance;
import com.mrozowski.softwarepartnertask.domain.model.Billing;
import com.mrozowski.softwarepartnertask.domain.model.Parent;
import com.mrozowski.softwarepartnertask.domain.model.ParentBilling;
import com.mrozowski.softwarepartnertask.domain.port.AttendanceRepository;
import com.mrozowski.softwarepartnertask.domain.port.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class BillingService {

  private final ParentRepository parentRepository;
  private final AttendanceRepository attendanceRepository;
  private final CostCalculatorService costCalculatorService;

  Billing<ParentBilling> processParentBilling(ParentBillingCommand command) {
    var parent = getParentById(command.parentId());
    var childIds = getChildIds(parent);

    var attendance = attendanceRepository.findByDateAndChildIds(childIds, command.startDate(), command.endDate());
    var childAttendanceMap = attendance.stream().collect(Collectors.groupingBy(Attendance::childId));

    var childBillingList = createChildBillingList(parent, childAttendanceMap);
    var parentBilling = ParentBilling.builder()
        .id(parent.id())
        .firstname(parent.firstname())
        .lastname(parent.lastname())
        .children(childBillingList)
        .totalCost(getTotalChildrenCost(childBillingList))
        .build();

    return new Billing<>(command.startDate(), command.endDate(), parentBilling);
  }

  private static long getTotalChildrenCost(List<ParentBilling.ChildBilling> childBillingList) {
    return childBillingList.stream().mapToLong(ParentBilling.ChildBilling::totalCost).sum();
  }

  private List<ParentBilling.ChildBilling> createChildBillingList(
      Parent parent, Map<Long, List<Attendance>> childAttendanceMap) {
    return parent.children().stream().map(e -> createChildBilling(e, childAttendanceMap.get(e.id()))).toList();
  }

  private ParentBilling.ChildBilling createChildBilling(Parent.Child child, List<Attendance> attendanceList) {
    var result = costCalculatorService.calculate(attendanceList, child.school().hourPriceInCent());
    return ParentBilling.ChildBilling.builder()
        .id(child.id())
        .firstname(child.firstname())
        .lastname(child.lastName())
        .numberOfHours(result.totalSpendHours())
        .totalCost(result.totalCost())
        .build();
  }

  private Parent getParentById(long parentId) {
    return parentRepository.findParentById(parentId)
        .orElseThrow(() -> new ParentNotFoundException(parentId));
  }

  private static List<Long> getChildIds(Parent parent) {
    return parent.children().stream().map(Parent.Child::id).toList();
  }
}
