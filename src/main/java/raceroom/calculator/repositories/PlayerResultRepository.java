package raceroom.calculator.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RepositoryRestResource(collectionResourceRel = "playerresult", path = "playerresults")
public interface PlayerResultRepository extends PagingAndSortingRepository<PlayerResultEntity, Long> {

    @Transactional
    List<PlayerResultEntity> getPlayersByEventIdAndSessionTypeOrderByPositionAsc(Long eventId, String sessionType);

    @Transactional
    List<PlayerResultEntity> getPlayersByEventIdAndSessionTypeOrderByBestLapTimeAsc(Long eventId, String sessionType);

    @Transactional
    PlayerResultEntity getPlayerEntityByEventIdAndSessionTypeAndPlayer(Long eventId, String sessionType, PlayerEntity player);
}
