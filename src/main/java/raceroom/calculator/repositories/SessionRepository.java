package raceroom.calculator.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import raceroom.calculator.model.Race;
import raceroom.calculator.model.Session;

import java.util.List;

@Transactional
@RepositoryRestResource(collectionResourceRel = "session", path = "sessions")
public interface SessionRepository extends PagingAndSortingRepository<Session, Long> {

    @Transactional
    List<Session> getSessionsByType(String type);

    @Transactional
    List<Session> getSessionsByRaceId(Long raceId);
}
