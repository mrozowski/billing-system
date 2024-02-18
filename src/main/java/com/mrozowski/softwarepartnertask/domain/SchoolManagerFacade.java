package com.mrozowski.softwarepartnertask.domain;

import com.mrozowski.softwarepartnertask.domain.command.ParentBillingCommand;
import com.mrozowski.softwarepartnertask.domain.model.Billing;
import com.mrozowski.softwarepartnertask.domain.model.ParentBilling;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchoolManagerFacade {

  private final BillingService billingService;

  public Billing<ParentBilling> processParentBilling(ParentBillingCommand command){
    return billingService.processParentBilling(command);
  }
}
