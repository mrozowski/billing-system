package com.mrozowski.softwarepartnertask.domain;

import com.mrozowski.softwarepartnertask.domain.command.BillingCommand;
import com.mrozowski.softwarepartnertask.domain.exception.ParentNotFoundException;
import com.mrozowski.softwarepartnertask.domain.exception.SchoolNotFoundException;
import com.mrozowski.softwarepartnertask.domain.model.*;
import com.mrozowski.softwarepartnertask.domain.port.AttendanceRepository;
import com.mrozowski.softwarepartnertask.domain.port.ParentRepository;
import com.mrozowski.softwarepartnertask.domain.port.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class BillingService {

  private final SchoolRepository schoolRepository;
  private final ParentRepository parentRepository;
  private final AttendanceRepository attendanceRepository;
  private final CostCalculatorService costCalculatorService;

  public Billing<SchoolBilling> processSchoolBilling(BillingCommand command) {
    log.info("Start processing billing for school with id [{}]", command.id());
    var school = getSchoolById(command.id());
    var parentsInSchool = parentRepository.findAllParentsBySchoolId(command.id());
    var parentBillingList = parentsInSchool.stream()
        .map(parent -> createParentBilling(command, getChildIds(parent), parent)).toList();

    var schoolBilling = SchoolBilling.builder()
        .id(school.id())
        .name(school.name())
        .parents(parentBillingList)
        .totalCostInCents(getTotalParentsCost(parentBillingList))
        .build();
    return new Billing<>(command.startDate(), command.endDate(), schoolBilling);
  }


  Billing<ParentBilling> processParentBilling(BillingCommand command) {
    log.info("Start processing billing for parent with id [{}]", command.id());
    var parent = getParentById(command.id());
    var childIds = getChildIds(parent);

    var parentBilling = createParentBilling(command, childIds, parent);
    return new Billing<>(command.startDate(), command.endDate(), parentBilling);
  }

  private ParentBilling createParentBilling(BillingCommand command, List<Long> childIds, Parent parent) {
    log.info("Calculating billing for parent with id [{}]", parent.id());
    var attendanceList = attendanceRepository.findByDateAndChildIds(childIds, command.startDate(), command.endDate());
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

  private Parent getParentById(long parentId) {
    return parentRepository.findParentById(parentId)
        .orElseThrow(() -> new ParentNotFoundException(parentId));
  }

  private School getSchoolById(long schoolId) {
    return schoolRepository.findById(schoolId)
        .orElseThrow(() -> new SchoolNotFoundException(schoolId));
  }

  private static List<Long> getChildIds(Parent parent) {
    return parent.children().stream().map(Parent.Child::id).toList();
  }
}
