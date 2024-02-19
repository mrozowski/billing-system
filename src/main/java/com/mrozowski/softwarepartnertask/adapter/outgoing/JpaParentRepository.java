package com.mrozowski.softwarepartnertask.adapter.outgoing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface JpaParentRepository extends JpaRepository<ParentEntity, Long> {

  @Query("SELECT p FROM ParentEntity p LEFT JOIN FETCH p.children c LEFT JOIN FETCH c.school WHERE p.id = :id")
  Optional<ParentEntity> findFirstById(long id);

  @Query("SELECT DISTINCT p FROM ParentEntity p JOIN FETCH p.children c JOIN FETCH c.school s WHERE s.id = :schoolId")
  List<ParentEntity> findAllBySchoolId(long schoolId);
}
