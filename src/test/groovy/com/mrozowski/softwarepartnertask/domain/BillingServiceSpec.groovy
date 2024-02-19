package com.mrozowski.softwarepartnertask.domain

import com.mrozowski.softwarepartnertask.domain.command.BillingCommand
import com.mrozowski.softwarepartnertask.domain.port.AttendanceRepository
import com.mrozowski.softwarepartnertask.domain.port.ParentRepository
import com.mrozowski.softwarepartnertask.domain.port.SchoolRepository
import spock.lang.Specification
import spock.lang.Subject

class BillingServiceSpec extends Specification {

  def parentRepository = Mock(ParentRepository)
  def schoolRepository = Mock(SchoolRepository)
  def attendanceRepository = Mock(AttendanceRepository)
  def billingCreator = Mock(BillingCreator)

  @Subject
  def underTest = new BillingService(schoolRepository, parentRepository, attendanceRepository, billingCreator)

  def 'should create billing for school'(){
    given:
    def attendanceList = [Fixtures.ATTENDANCE]
    def command = new BillingCommand(Fixtures.SCHOOL_ID, Fixtures.START_DATE, Fixtures.END_DATE)

    when:
    def result = underTest.processSchoolBilling(command)

    then:
    1 * schoolRepository.findById(Fixtures.SCHOOL_ID) >> Optional.of(Fixtures.SCHOOL)
    1 * parentRepository.findAllParentsBySchoolId(Fixtures.SCHOOL_ID) >> [Fixtures.PARENT]
    1 * attendanceRepository.findByDateAndChildIds([Fixtures.CHILD_ID], Fixtures.START_DATE, Fixtures.END_DATE) >> attendanceList
    1 * billingCreator.createParentBilling(Fixtures.PARENT, attendanceList) >> Fixtures.PARENT_BILLING
    1 * billingCreator.createSchoolBilling(Fixtures.SCHOOL, [Fixtures.PARENT_BILLING]) >> Fixtures.SCHOOL_BILLING

    result.startDate() == Fixtures.START_DATE
    result.endDate() == Fixtures.END_DATE
    with(result.billingSubject()){
      it.id() == Fixtures.SCHOOL_ID
      it.name() == Fixtures.SCHOOL_NAME
      it.totalCostInCents() == Fixtures.TOTAL_COST
      it.parents().size() == 1
      it.parents().first().children().size() == 1
    }
  }

  def 'should create billing for parent'() {
    given:
    def attendanceList = [Fixtures.ATTENDANCE]
    def command = new BillingCommand(Fixtures.PARENT_ID, Fixtures.START_DATE, Fixtures.END_DATE)

    when:
    def result = underTest.processParentBilling(command)

    then:
    1 * parentRepository.findParentById(Fixtures.PARENT_ID) >> Optional.of(Fixtures.PARENT)
    1 * attendanceRepository.findByDateAndChildIds([Fixtures.CHILD_ID], Fixtures.START_DATE, Fixtures.END_DATE) >> attendanceList
    1 * billingCreator.createParentBilling(Fixtures.PARENT, attendanceList) >> Fixtures.PARENT_BILLING

    result.startDate() == Fixtures.START_DATE
    result.endDate() == Fixtures.END_DATE
    with(result.billingSubject()) {
      it.id() == Fixtures.PARENT_ID
      it.totalCostInCents() == Fixtures.TOTAL_COST

      with(it.children().first()) {
        it.id() == Fixtures.CHILD_ID
        it.firstname() == Fixtures.CHILD_FIRST_NAME
        it.lastname() == Fixtures.PARENT_LAST_NAME
        it.totalCostInCents() == Fixtures.TOTAL_COST
        it.payedHours() == Fixtures.TOTAL_PAYED_HOURS
      }
    }
  }
}
