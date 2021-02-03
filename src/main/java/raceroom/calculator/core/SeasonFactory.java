package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.model.Season;
import raceroom.calculator.repositories.EventRepository;
import raceroom.calculator.repositories.PlayerEntity;
import raceroom.calculator.repositories.PlayerRepository;
import raceroom.calculator.repositories.SeasonRepository;
import raceroom.calculator.rest.EventDTO;

import java.util.List;

@Slf4j
@Component
public class SeasonFactory {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    public void seasonBuilder(EventDTO eventDTO) {
        List<PlayerEntity> driversForRace = getDriversForRace(eventDTO);
        for (PlayerEntity driver:driversForRace) {
            Season season = getUsedOrNewSeason(driver, eventDTO);
            season.setSeasonName(eventDTO.getServer());
            season.setDriver(driver.getFullName());
            season.setSeasonPoints(getDriverChampionshipPoints(season) + getDriverQualifyPoints(eventDTO, driver) + driver.getPoints());
            seasonRepository.save(season);
        }
        log.info("season results are calculated");
    }

    private int getDriverQualifyPoints(EventDTO eventDTO, PlayerEntity driver) {
        return playerRepository.getPlayerEntityByEventIdAndSessionTypeAndFullName(
                eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                        eventDTO.getServer(),
                        eventDTO.getTrack(),
                        eventDTO.getTrackLayout()).getId(), "Qualify", driver.getFullName()).getPoints();
    }

    private Season getUsedOrNewSeason(PlayerEntity driver, EventDTO eventDTO) {
        Season season = seasonRepository.getSeasonByDriverAndSeasonName(driver.getFullName(), eventDTO.getServer());
        if (season == null) {
            season = new Season();
        }
        return season;
    }

    private int getDriverChampionshipPoints(Season season) {
        return season == null ?
                0 : season.getSeasonPoints();
    }

    private List<PlayerEntity> getDriversForRace(EventDTO eventDTO) {
        return playerRepository.getPlayersByEventIdAndSessionTypeOrderByPositionAsc(
                eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                        eventDTO.getServer(),
                        eventDTO.getTrack(),
                        eventDTO.getTrackLayout()).getId(),
                "Race"
        );
    }
}
