package com.mrozowski.softwarepartnertask.adapter.outgoing

import spock.lang.Specification
import spock.lang.Subject

import java.time.OffsetDateTime

class RepositoryMapperSpec extends Specification {

  @Subject
  def underTest = new RepositoryMapper()

  def 'should map entity to parent'() {
    given:
    def schoolEntity = new SchoolEntity(id: 1l, name: "Sunny School", hourPrice: 250)
    def childrenEntity = new ChildEntity(id: 1l, firstname: "Zdzisław", lastname: "Marecki", school: schoolEntity)
    def parentEntity = new ParentEntity(id: 1l, firstname: "Adam", lastname: "Marecki", children: [childrenEntity])

    when:
    def result = underTest.entityToParent(parentEntity)

    then:
    result.id() == 1l
    result.firstname() == "Adam"
    result.lastname() == "Marecki"
    with(result.children().first()) {
      it.id() == 1l
      it.firstname() == "Zdzisław"
      it.lastName() == "Marecki"
      it.school().name() == "Sunny School"
      it.school().hourPriceInCent() == 250
    }
  }

  def 'should map entity to attendance'() {
    given:
    def exitDate = OffsetDateTime.now()
    def entryDate = exitDate.minusHours(4)
    def entity = new AttendanceEntity(id: 1l, childId: 1l, entryDate: entryDate, exitDate: exitDate)

    when:
    def result = underTest.entityToAttendance(entity)

    then:
    result.id() == 1l
    result.childId() == 1l
    result.entryDate() == entryDate
    result.exitDate() == exitDate
  }
}
