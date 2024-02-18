package com.mrozowski.softwarepartnertask.adapter.outgoing;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "school")
class SchoolEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private int hourPrice;
}
