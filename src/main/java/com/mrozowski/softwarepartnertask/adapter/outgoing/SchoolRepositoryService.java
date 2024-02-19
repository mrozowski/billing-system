package com.mrozowski.softwarepartnertask.adapter.outgoing;

import com.mrozowski.softwarepartnertask.domain.model.School;
import com.mrozowski.softwarepartnertask.domain.port.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class SchoolRepositoryService implements SchoolRepository {

  private final JpaSchoolRepository jpaSchoolRepository;
  private final RepositoryMapper mapper;

  @Override
  public Optional<School> findById(long id) {
    return jpaSchoolRepository.findById(id).map(mapper::entityToSchool);
  }
}
