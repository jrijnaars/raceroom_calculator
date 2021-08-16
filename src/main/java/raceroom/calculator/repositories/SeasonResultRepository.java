package raceroom.calculator.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RepositoryRestResource(collectionResourceRel = "seasonresult", path = "seasonresults")
public interface SeasonResultRepository extends PagingAndSortingRepository<SeasonResultEntity, Long> {

    @Transactional
    SeasonResultEntity getSeasonByPlayerAndSeasonName(PlayerEntity player, String seasonname);

}
