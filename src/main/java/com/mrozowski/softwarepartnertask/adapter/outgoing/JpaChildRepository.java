package com.mrozowski.softwarepartnertask.adapter.outgoing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaChildRepository extends JpaRepository<ChildEntity, Long> {

}
