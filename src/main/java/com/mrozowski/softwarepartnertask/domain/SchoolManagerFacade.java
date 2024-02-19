package com.mrozowski.softwarepartnertask.domain;

import com.mrozowski.softwarepartnertask.domain.command.BillingCommand;
import com.mrozowski.softwarepartnertask.domain.model.Billing;
import com.mrozowski.softwarepartnertask.domain.model.ParentBilling;
import com.mrozowski.softwarepartnertask.domain.model.SchoolBilling;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchoolManagerFacade {

  private final BillingService billingService;

  public Billing<ParentBilling> processParentBilling(BillingCommand command){
    return billingService.processParentBilling(command);
  }

  public Billing<SchoolBilling> processSchoolBilling(BillingCommand command){
    return billingService.processSchoolBilling(command);
  }
}
