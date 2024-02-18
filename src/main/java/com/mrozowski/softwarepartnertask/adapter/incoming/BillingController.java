package com.mrozowski.softwarepartnertask.adapter.incoming;


import com.mrozowski.softwarepartnertask.domain.SchoolManagerFacade;
import com.mrozowski.softwarepartnertask.domain.command.ParentBillingCommand;
import com.mrozowski.softwarepartnertask.domain.model.Billing;
import com.mrozowski.softwarepartnertask.domain.model.ParentBilling;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Slf4j
@RestController
@RequestMapping("v1/billing")
@RequiredArgsConstructor
class BillingController {

  private final SchoolManagerFacade schoolManagerFacade;

  @GetMapping(value = "/parent")
  ResponseEntity<Billing<ParentBilling>> createMonthlyBillingForParent(
      @RequestParam long id, @RequestParam int month, @RequestParam int year) {
    log.info("Received request for monthly parent billing with id [{}] and month [{},{}]", id, month, year);

    var startDate = createOffsetDateTimeForMonth(year, month);
    var endDate = startDate.plusMonths(1);
    var command = new ParentBillingCommand(id, startDate, endDate);
    return ResponseEntity.ok(schoolManagerFacade.processParentBilling(command));
  }

  private OffsetDateTime createOffsetDateTimeForMonth(int year, int month){
    return OffsetDateTime.of(year, month, 1, 0, 0, 0, 0, ZoneOffset.UTC);
  }
}
