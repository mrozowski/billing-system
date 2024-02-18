package com.mrozowski.softwarepartnertask.adapter.outgoing;

import com.mrozowski.softwarepartnertask.domain.model.Attendance;
import com.mrozowski.softwarepartnertask.domain.port.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
class AttendanceRepositoryService implements AttendanceRepository {
  @Override
  public List<Attendance> findByDateAndChildIds(List<Long> childIds, OffsetDateTime startDate, OffsetDateTime endDate) {
    // fetch data from db
    return null;
  }
}
