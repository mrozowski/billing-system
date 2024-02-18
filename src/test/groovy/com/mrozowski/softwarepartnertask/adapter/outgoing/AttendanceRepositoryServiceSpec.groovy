package com.mrozowski.softwarepartnertask.adapter.outgoing

import com.mrozowski.softwarepartnertask.domain.model.Attendance
import spock.lang.Specification
import spock.lang.Subject

import java.time.OffsetDateTime
import java.util.stream.Stream

class AttendanceRepositoryServiceSpec extends Specification {

  def attendanceRepository = Mock(JpaAttendanceRepository)
  def mapper = Mock(RepositoryMapper)
  @Subject
  def underTest = new AttendanceRepositoryService(attendanceRepository, mapper)

  def 'should find and map attendance entity list'() {
    given:
    def childrenIds = [1l]
    def endDate = OffsetDateTime.now()
    def startDate = endDate.minusMonths(1)
    def entity = new AttendanceEntity()
    def attendance = Attendance.builder().build()

    when:
    def result = underTest.findByDateAndChildIds(childrenIds, startDate, endDate)

    then:
    1 * attendanceRepository.findByEntryDateBetweenAndChildIdIn(startDate, endDate, childrenIds) >> Stream.of(entity)
    1 * mapper.entityToAttendance(entity) >> attendance
    result == [attendance]
  }
}
