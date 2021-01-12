package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.model.Season;
import raceroom.calculator.repositories.PlayerEntity;
import raceroom.calculator.repositories.PlayerRepository;
import raceroom.calculator.repositories.RaceRepository;
import raceroom.calculator.repositories.SeasonRepository;
import raceroom.calculator.rest.RaceDTO;

import java.util.List;

@Slf4j
@Component
public class SeasonFactory {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    public void seasonBuilder(RaceDTO raceDTO) {
        List<PlayerEntity> driversForRace = getDriversForRace(raceDTO);
        for (PlayerEntity driver:driversForRace) {
            Season season = getUsedOrNewSeason(driver, raceDTO);
            season.setSeasonName(raceDTO.getServer());
            season.setDriver(driver.getFullName());
            season.setSeasonPoints(getDriverChampionshipPoints(season) + getDriverQualifyPoints(raceDTO, driver) + driver.getPoints());
            seasonRepository.save(season);
        }

    }

    private int getDriverQualifyPoints(RaceDTO raceDTO, PlayerEntity driver) {
        return playerRepository.getPlayerEntityByRaceIdAndSessionTypeAndFullName(
                raceRepository.getRaceByServerAndTrackAndTrackLayout(
                        raceDTO.getServer(),
                        raceDTO.getTrack(),
                        raceDTO.getTrackLayout()).getId(), "Qualify", driver.getFullName()).getPoints();
    }

    private Season getUsedOrNewSeason(PlayerEntity driver, RaceDTO raceDTO) {
        Season season = seasonRepository.getSeasonByDriverAndSeasonName(driver.getFullName(), raceDTO.getServer());
        if (season == null) {
            season = new Season();
        }
        return season;
    }

    private int getDriverChampionshipPoints(Season season) {
        return season == null ?
                0 : season.getSeasonPoints();
    }

    private List<PlayerEntity> getDriversForRace(RaceDTO raceDTO) {
        return playerRepository.getPlayersByRaceIdAndAndSessionTypeOrderByPositionAsc(
                raceRepository.getRaceByServerAndTrackAndTrackLayout(
                        raceDTO.getServer(),
                        raceDTO.getTrack(),
                        raceDTO.getTrackLayout()).getId(),
                "Race"
        );
    }
}
