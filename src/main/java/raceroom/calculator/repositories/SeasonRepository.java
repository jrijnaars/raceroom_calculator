package raceroom.calculator.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import raceroom.calculator.model.Season;

@Transactional
@RepositoryRestResource(collectionResourceRel = "season", path = "seasons")
public interface SeasonRepository extends PagingAndSortingRepository<Season, Long> {

    @Transactional
    Season getSeasonByDriverAndSeasonName(String driver, String seasonname);

}
