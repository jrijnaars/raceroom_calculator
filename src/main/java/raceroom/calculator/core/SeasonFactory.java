package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.*;
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
        updateQuallifyPoints(eventDTO, getDriversForRace1(eventDTO));
        updateRacePoints(eventDTO, getDriversForRace1(eventDTO));
        updateRacePoints(eventDTO, getDriversForRace2(eventDTO));
        log.info("season results are calculated");
    }

    private void updateQuallifyPoints(EventDTO eventDTO, List<PlayerEntity> driversForRace1) {
        for (PlayerEntity driver:driversForRace1) {
            SeasonEntity season = getUsedOrNewSeason(driver, eventDTO);
            season.setSeasonName(eventDTO.getServer());
            season.setDriver(driver.getFullName());
            int totalpoints = getDriverChampionshipPoints(season);
            totalpoints = totalpoints + getDriverQualifyPoints(eventDTO, driver);
            season.setSeasonPoints(totalpoints);
            seasonRepository.save(season);
        }
    }

    private void updateRacePoints(EventDTO eventDTO, List<PlayerEntity> driversForRace1) {
        for (PlayerEntity driver:driversForRace1) {
            SeasonEntity season = getUsedOrNewSeason(driver, eventDTO);
            season.setSeasonName(eventDTO.getServer());
            season.setDriver(driver.getFullName());
            season.setSeasonPoints(getTotalpoints(driver, season));
            season.setCarname(driver.getCar());
            seasonRepository.save(season);
        }
    }

    private int getTotalpoints(PlayerEntity driver, SeasonEntity season) {
        int totalpoints = getDriverChampionshipPoints(season);

        if (!checkCarUsedBefore(driver, season)){
            log.info("driver: " + driver.getFullName() + " changed from car: " + season.getCarname() + " to " + driver.getCar());
            log.info("so total points of: " + totalpoints + " are lost!");
            totalpoints = 0;
        }
        totalpoints = totalpoints + driver.getPoints();
        return totalpoints;
    }

    private boolean checkCarUsedBefore(PlayerEntity driver, SeasonEntity season) {
        return driver.getCar().equals(season.getCarname()) || season.getCarname() == null;
    }

    private int getFastestLapPoints(PlayerEntity driver) {
     if (driver.isFastestLap()) {
         return 6;
     } else {
         return 0;
     }
    }

    private int getDriverQualifyPoints(EventDTO eventDTO, PlayerEntity driver) {
        return playerRepository.getPlayerEntityByEventIdAndSessionTypeAndFullName(
                eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                        eventDTO.getServer(),
                        eventDTO.getTrack(),
                        eventDTO.getTrackLayout()).getId(), "Qualify", driver.getFullName()).getPoints();
    }

    private SeasonEntity getUsedOrNewSeason(PlayerEntity driver, EventDTO eventDTO) {
        SeasonEntity season = seasonRepository.getSeasonByDriverAndSeasonName(driver.getFullName(), eventDTO.getServer());
        if (season == null) {
            season = new SeasonEntity();
        }
        return season;
    }

    private int getDriverChampionshipPoints(SeasonEntity season) {
        return season == null ?
                0 : season.getSeasonPoints();
    }

    private List<PlayerEntity> getDriversForRace1(EventDTO eventDTO) {
        return playerRepository.getPlayersByEventIdAndSessionTypeOrderByPositionAsc(
                eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                        eventDTO.getServer(),
                        eventDTO.getTrack(),
                        eventDTO.getTrackLayout()).getId(),
                "Race"
        );
    }

    private List<PlayerEntity> getDriversForRace2(EventDTO eventDTO) {
        return playerRepository.getPlayersByEventIdAndSessionTypeOrderByPositionAsc(
                eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                        eventDTO.getServer(),
                        eventDTO.getTrack(),
                        eventDTO.getTrackLayout()).getId(),
                "Race2"
        );
    }
}
