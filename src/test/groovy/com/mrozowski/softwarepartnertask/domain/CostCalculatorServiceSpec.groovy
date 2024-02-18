package com.mrozowski.softwarepartnertask.domain

import com.mrozowski.softwarepartnertask.domain.model.Attendance
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalTime

class CostCalculatorServiceSpec extends Specification {

  def startFreeTime = LocalTime.of(7, 0)
  def endFreeTime = LocalTime.of(12, 0)

  @Subject
  def underTest = new CostCalculatorService(startFreeTime, endFreeTime)

  def 'should calculate cost for a given attendance list'() {
    given:
    def enterDatetime = Fixtures.createDefaultDatetimeFromTime(enterHour, enterMinut)
    def exitDatetime = Fixtures.createDefaultDatetimeFromTime(exitHour, exitMinut)
    def attendanceList = [new Attendance(1L, 1L, enterDatetime, exitDatetime)]
    def hourCostInCents = 250

    and:
    def result = underTest.calculate(attendanceList, hourCostInCents)

    expect:
    result.totalSpendHours() == totalSpendHours
    result.payedHours() == payedHours
    result.totalCost() == payedHours * hourCostInCents

    where:
    enterHour | enterMinut | exitHour | exitMinut | totalSpendHours | payedHours | totalCost
    6         | 59         | 12       | 01        | 7               | 2          | 500
    7         | 00         | 12       | 00        | 5               | 0          | 0
    7         | 00         | 12       | 59        | 6               | 1          | 250
    7         | 00         | 13       | 00        | 6               | 1          | 250
    7         | 00         | 13       | 01        | 7               | 2          | 500
    6         | 30         | 14       | 30        | 9               | 4          | 1000
  }
}
