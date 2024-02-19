package com.mrozowski.softwarepartnertask.domain.port;

import com.mrozowski.softwarepartnertask.domain.model.Parent;

import java.util.List;
import java.util.Optional;

public interface ParentRepository {

  Optional<Parent> findParentById(long id);

  List<Parent> findAllParentsBySchoolId(long schoolId);
}
