package com.mrozowski.softwarepartnertask.adapter.outgoing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface JpaParentRepository extends JpaRepository<ParentEntity, Long> {

  Optional<ParentEntity> findFirstById(long id);

  @Query("SELECT p FROM ParentEntity p JOIN ChildEntity c ON p.id = c.parent.id WHERE c.school.id = :schoolId")
  List<ParentEntity> findAllBySchoolId(long schoolId);
}
