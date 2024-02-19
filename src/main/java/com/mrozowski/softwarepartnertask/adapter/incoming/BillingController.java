package com.mrozowski.softwarepartnertask.adapter.incoming;


import com.mrozowski.softwarepartnertask.domain.SchoolManagerFacade;
import com.mrozowski.softwarepartnertask.domain.command.BillingCommand;
import com.mrozowski.softwarepartnertask.domain.exception.ParentNotFoundException;
import com.mrozowski.softwarepartnertask.domain.exception.SchoolNotFoundException;
import com.mrozowski.softwarepartnertask.domain.model.Billing;
import com.mrozowski.softwarepartnertask.domain.model.ParentBilling;
import com.mrozowski.softwarepartnertask.domain.model.SchoolBilling;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    var command = new BillingCommand(id, startDate, endDate);
    return ResponseEntity.ok(schoolManagerFacade.processParentBilling(command));
  }

  @GetMapping(value = "/school")
  ResponseEntity<Billing<SchoolBilling>> createMonthlyBillingForSchool(
      @RequestParam long id, @RequestParam int month, @RequestParam int year) {
    log.info("Received request for monthly school billing with id [{}] and month [{},{}]", id, month, year);

    var startDate = createOffsetDateTimeForMonth(year, month);
    var endDate = startDate.plusMonths(1);
    var command = new BillingCommand(id, startDate, endDate);
    return ResponseEntity.ok(schoolManagerFacade.processSchoolBilling(command));
  }

  private OffsetDateTime createOffsetDateTimeForMonth(int year, int month){
    return OffsetDateTime.of(year, month, 1, 0, 0, 0, 0, ZoneOffset.UTC);
  }

  @ExceptionHandler({ParentNotFoundException.class, SchoolNotFoundException.class})
  ResponseEntity<ApiError> handleIDateTimeParseException(RuntimeException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        ApiError.builder()
            .timestamp(LocalDateTime.now())
            .error("NOT_FOUND")
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .status(HttpStatus.NOT_FOUND)
            .build()
    );
  }
}
