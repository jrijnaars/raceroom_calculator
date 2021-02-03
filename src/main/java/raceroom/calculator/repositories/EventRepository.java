package raceroom.calculator.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RepositoryRestResource(collectionResourceRel = "event", path = "events")
public interface EventRepository extends PagingAndSortingRepository<EventEntity, Long> {

    @Transactional
    EventEntity getEventEntityByServerAndTrackAndTrackLayout(String server, String track, String tracklayout);

}
