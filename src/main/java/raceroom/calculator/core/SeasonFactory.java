package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.*;
import raceroom.calculator.rest.EventDTO;
import raceroom.calculator.rest.SessionDTO;

import java.util.List;

@Slf4j
@Component
public class SeasonFactory extends CalculatorFactory {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    public void seasonBuilder(EventDTO eventDTO) {
        for (SessionDTO session: eventDTO.getSessions()) {
            if (session.getType().equals("Qualify")) {
                updateQuallifyPoints(eventDTO, getDriversForRace(eventDTO, session));
            } else {
                updateRacePoints(eventDTO, getDriversForRace(eventDTO, session));
            }
        }
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

    private List<PlayerEntity> getDriversForRace(EventDTO eventDTO, SessionDTO session) {
        return playerRepository.getPlayersByEventIdAndSessionTypeOrderByPositionAsc(
                eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                        getShortServername(eventDTO.getServer()),
                        eventDTO.getTrack(),
                        eventDTO.getTrackLayout()).getId(),
                session.getType());
    }
}
