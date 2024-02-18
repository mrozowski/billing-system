package com.mrozowski.softwarepartnertask.adapter.outgoing;

import com.mrozowski.softwarepartnertask.domain.model.Attendance;
import com.mrozowski.softwarepartnertask.domain.model.Parent;
import com.mrozowski.softwarepartnertask.domain.model.School;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class RepositoryMapper {

  Parent entityToParent(ParentEntity entity) {
    return Parent.builder()
        .id(entity.getId())
        .firstname(entity.getFirstname())
        .lastname(entity.getLastname())
        .children(toChildList(entity.getChildren()))
        .build();
  }

  Attendance entityToAttendance(AttendanceEntity entity){
    return Attendance.builder()
        .id(entity.getId())
        .childId(entity.getChildId())
        .entryDate(entity.getEntryDate())
        .exitDate(entity.getExitDate())
        .build();
  }

  private static List<Parent.Child> toChildList(List<ChildEntity> children) {
    return children.stream().map(RepositoryMapper::toChild).toList();
  }

  private static Parent.Child toChild(ChildEntity childEntity) {
    return Parent.Child.builder()
        .id(childEntity.getId())
        .firstname(childEntity.getFirstname())
        .lastName(childEntity.getLastname())
        .school(toSchool(childEntity.getSchool()))
        .build();
  }

  private static School toSchool(SchoolEntity entity) {
    return new School(entity.getId(), entity.getName(), entity.getHourPrice());
  }
}
