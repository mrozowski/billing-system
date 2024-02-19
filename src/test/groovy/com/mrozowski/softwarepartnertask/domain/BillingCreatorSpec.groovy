package com.mrozowski.softwarepartnertask.domain

import spock.lang.Specification
import spock.lang.Subject

class BillingCreatorSpec extends Specification {

  def costCalculatorService = Mock(CostCalculatorService)

  @Subject
  def underTest = new BillingCreator(costCalculatorService)

  def 'should create SchoolBilling object'() {
    when:
    def result = underTest.createSchoolBilling(Fixtures.SCHOOL, [Fixtures.PARENT_BILLING])

    then:
    result.totalCostInCents() == Fixtures.TOTAL_COST
    result.parents() == [Fixtures.PARENT_BILLING]
    result.name() == Fixtures.SCHOOL_NAME
  }

  def 'should create ParentBilling object'() {
    given:
    def attendanceList = [Fixtures.ATTENDANCE]

    when:
    def result = underTest.createParentBilling(Fixtures.PARENT, attendanceList)

    then:
    1 * costCalculatorService.calculate(attendanceList, Fixtures.HOUR_PRICE_IN_CENT) >> Fixtures.CALCULATION_RESULT

    result.totalCostInCents() == Fixtures.TOTAL_COST
    result.children() == [Fixtures.CHILD_BILLING]
    result.firstname() == Fixtures.PARENT_FIST_NAME
    result.lastname() == Fixtures.PARENT_LAST_NAME
  }
}
