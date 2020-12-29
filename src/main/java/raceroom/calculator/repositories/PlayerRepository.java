package raceroom.calculator.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import raceroom.calculator.model.Player;
import raceroom.calculator.model.Race;

import java.util.List;

@Transactional
@RepositoryRestResource(collectionResourceRel = "player", path = "players")
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

    @Transactional
    List<Player> getPlayersByRaceIdAndAndSessionTypeOrderByPositionAsc(Long raceId, String sessionType);
}
