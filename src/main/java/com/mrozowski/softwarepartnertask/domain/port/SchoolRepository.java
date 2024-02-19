package com.mrozowski.softwarepartnertask.domain.port;

import com.mrozowski.softwarepartnertask.domain.model.School;

import java.util.Optional;

public interface SchoolRepository {

  Optional<School> findById(long id);
}
