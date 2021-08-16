package raceroom.calculator.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RepositoryRestResource(collectionResourceRel = "season", path = "seasons")
public interface SeasonRepository extends PagingAndSortingRepository<SeasonEntity, Long> {

    @Transactional
    SeasonEntity findSeasonEntityByName(String seasonname);

}
