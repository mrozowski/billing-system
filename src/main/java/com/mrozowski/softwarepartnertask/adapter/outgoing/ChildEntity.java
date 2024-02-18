package com.mrozowski.softwarepartnertask.adapter.outgoing;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "child")
class ChildEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String firstname;
  private String lastname;
  @ManyToOne
  @JoinColumn(name = "parent_id", referencedColumnName = "id")
  @ToString.Exclude
  private ParentEntity parent;
  @ManyToOne
  @JoinColumn(name = "school_id", referencedColumnName = "id")
  private SchoolEntity school;
}
