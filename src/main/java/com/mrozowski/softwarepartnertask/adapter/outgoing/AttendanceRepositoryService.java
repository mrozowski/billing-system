package com.mrozowski.softwarepartnertask.adapter.outgoing;

import com.mrozowski.softwarepartnertask.domain.model.Attendance;
import com.mrozowski.softwarepartnertask.domain.port.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class AttendanceRepositoryService implements AttendanceRepository {

  private final JpaAttendanceRepository jpaAttendanceRepository;
  private final RepositoryMapper repositoryMapper;

  @Override
  @Transactional(readOnly = true)
  public List<Attendance> findByDateAndChildIds(List<Long> childIds, OffsetDateTime startDate, OffsetDateTime endDate) {
    return jpaAttendanceRepository.findByEntryDateBetweenAndChildIdIn(startDate, endDate, childIds)
        .map(repositoryMapper::entityToAttendance)
        .toList();
  }
}
