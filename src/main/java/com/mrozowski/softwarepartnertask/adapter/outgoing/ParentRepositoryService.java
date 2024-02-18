package com.mrozowski.softwarepartnertask.adapter.outgoing;

import com.mrozowski.softwarepartnertask.domain.model.Parent;
import com.mrozowski.softwarepartnertask.domain.port.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ParentRepositoryService implements ParentRepository {


  @Override
  public Optional<Parent> findParentById(long id) {
    return Optional.empty();
  }
}
