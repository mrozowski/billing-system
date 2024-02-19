package com.mrozowski.softwarepartnertask.adapter.outgoing;

import com.mrozowski.softwarepartnertask.domain.model.Parent;
import com.mrozowski.softwarepartnertask.domain.port.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class ParentRepositoryService implements ParentRepository {

  private final JpaParentRepository jpaParentRepository;
  private final RepositoryMapper repositoryMapper;

  @Override
  public Optional<Parent> findParentById(long id) {
    return jpaParentRepository.findFirstById(id).map(repositoryMapper::entityToParent);
  }

  @Override
  public List<Parent> findAllParentsBySchoolId(long schoolId) {
    return jpaParentRepository.findAllBySchoolId(schoolId).stream().map(repositoryMapper::entityToParent).toList();
  }
}

