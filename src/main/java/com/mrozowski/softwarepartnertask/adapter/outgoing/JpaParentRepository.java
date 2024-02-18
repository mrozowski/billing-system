package com.mrozowski.softwarepartnertask.adapter.outgoing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface JpaParentRepository extends JpaRepository<ParentEntity, Long> {

  Optional<ParentEntity> findFirstById(long id);
}
