package com.mrozowski.softwarepartnertask.adapter.outgoing;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "attendance")
class AttendanceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private long childId;
  private OffsetDateTime entryDate;
  private OffsetDateTime exitDate;
}
