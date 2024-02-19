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
  private final BillingCreator billingCreator;

  Billing<SchoolBilling> processSchoolBilling(BillingCommand command) {
    log.info("Start processing billing for school with id [{}]", command.id());
    var school = getSchoolById(command.id());
    var parentsInSchool = parentRepository.findAllParentsBySchoolId(command.id());
    log.info("Calculating billing for school with id [{}]", school.id());
    var parentBillingList = parentsInSchool.stream()
        .map(parent -> createParentBilling(command, getChildIds(parent), parent)).toList();

    var schoolBilling = billingCreator.createSchoolBilling(school, parentBillingList);
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
    log.info("Fetching attendance list for all children with parentId [{}]", parent.id());
    var attendanceList = attendanceRepository.findByDateAndChildIds(childIds, command.startDate(), command.endDate());
    return billingCreator.createParentBilling(parent, attendanceList);
  }

  private Parent getParentById(long parentId) {
    return parentRepository.findParentById(parentId)
        .orElseThrow(() -> {
          log.error("Parent with id [{}] not found", parentId);
          return new ParentNotFoundException(parentId);
        });
  }

  private School getSchoolById(long schoolId) {
    return schoolRepository.findById(schoolId)
        .orElseThrow(() -> {
          log.error("School with id [{}] not found", schoolId);
          return new SchoolNotFoundException(schoolId);
        });
  }

  private static List<Long> getChildIds(Parent parent) {
    return parent.children().stream().map(Parent.Child::id).toList();
  }
}
