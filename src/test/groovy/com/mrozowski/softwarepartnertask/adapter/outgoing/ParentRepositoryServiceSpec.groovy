package com.mrozowski.softwarepartnertask.adapter.outgoing

import com.mrozowski.softwarepartnertask.domain.model.Parent
import spock.lang.Specification
import spock.lang.Subject

class ParentRepositoryServiceSpec extends Specification {

  def parentRepository = Mock(JpaParentRepository)
  def mapper = Mock(RepositoryMapper)
  @Subject
  def underTest = new ParentRepositoryService(parentRepository, mapper)

  def 'should find and map parent entity'() {
    given:
    def parentId = 1l
    def parentEntity = new ParentEntity()
    def parent = Parent.builder().build()

    when:
    def result = underTest.findParentById(parentId)

    then:
    1 * parentRepository.findFirstById(parentId) >> Optional.of(parentEntity)
    1 * mapper.entityToParent(parentEntity) >> parent
    result.isPresent()
    result.get() == parent
  }
}
