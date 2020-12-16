package raceroom.calculator.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import raceroom.calculator.model.Race;

@Transactional
@RepositoryRestResource(collectionResourceRel = "race", path = "races")
public interface RaceRepository extends PagingAndSortingRepository<Race, Long> {

    @Transactional
    Race getRaceByServerAndTrackAndTrackLayout(String server, String track, String tracklayout);

}
