package raceroom.calculator.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RepositoryRestResource(collectionResourceRel = "session", path = "sessions")
public interface SessionRepository extends PagingAndSortingRepository<SessionEntity, Long> {

    @Transactional
    List<SessionEntity> getSessionsByType(String type);

    @Transactional
    List<SessionEntity> getSessionsByEventId(Long eventId);
}
