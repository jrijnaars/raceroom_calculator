package raceroom.calculator.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RepositoryRestResource(collectionResourceRel = "eventResult", path = "eventResults")
public interface EventResultsRepository extends PagingAndSortingRepository<EventResultEntity, Long> {

    @Transactional
    EventResultEntity getEventResultEntityByEventNameAndDriverName(String eventName, String driverName);

    List<EventResultEntity> getEventResultEntitiesByEventNameOrderByEventPointsDesc(String eventname);

}
