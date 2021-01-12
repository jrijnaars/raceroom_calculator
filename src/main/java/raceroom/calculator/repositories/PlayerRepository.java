package raceroom.calculator.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RepositoryRestResource(collectionResourceRel = "player", path = "players")
public interface PlayerRepository extends PagingAndSortingRepository<PlayerEntity, Long> {

    @Transactional
    List<PlayerEntity> getPlayersByRaceIdAndAndSessionTypeOrderByPositionAsc(Long raceId, String sessionType);

    @Transactional
    PlayerEntity getPlayerEntityByRaceIdAndSessionTypeAndFullName(Long raceId, String sessionType, String drivername);
}
