package com.mrozowski.softwarepartnertask.domain.port;

import com.mrozowski.softwarepartnertask.domain.model.Attendance;

import java.time.OffsetDateTime;
import java.util.List;

public interface AttendanceRepository {

  List<Attendance> findByDateAndChildIds(List<Long> childIds, OffsetDateTime startDate, OffsetDateTime endDate);
}
