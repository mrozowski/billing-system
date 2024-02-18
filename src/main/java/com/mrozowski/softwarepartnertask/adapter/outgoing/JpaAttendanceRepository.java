package com.mrozowski.softwarepartnertask.adapter.outgoing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
interface JpaAttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

  Stream<AttendanceEntity> findByEntryDateBetweenAndChildIdIn(
      OffsetDateTime startDate, OffsetDateTime endDate, List<Long> childIds);
}
