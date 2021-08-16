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
    private SeasonResultRepository seasonResultRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    public void seasonResultsBuilder(EventDTO eventDTO, SeasonEntity seasonEntity) {
        for (SessionDTO session: eventDTO.getSessions()) {
            if (session.getType().equals("Qualify")) {
                updateQuallifyPoints(eventDTO, getDriversForRace(eventDTO, session), seasonEntity);
            } else {
                updateRacePoints(eventDTO, getDriversForRace(eventDTO, session), seasonEntity);
            }
        }
        log.info("seasonresults results are calculated");
    }


    public SeasonEntity seasonBuilder(EventDTO eventDTO) {
        SeasonEntity seasonEntity = seasonRepository.findSeasonEntityByName(eventDTO.getServer());
        if (seasonEntity == null) {
            seasonEntity = new SeasonEntity();
            seasonEntity.setName(eventDTO.getServer());
            seasonRepository.save(seasonEntity);
        }
        log.info("season succesfull in database");
        return seasonEntity;
    }

    private void updateQuallifyPoints(EventDTO eventDTO, List<PlayerEntity> driversForRace1, SeasonEntity seasonEntity) {
        for (PlayerEntity driver:driversForRace1) {
            SeasonResultEntity season = getUsedOrNewSeason(driver, eventDTO);
            season.setSeason(seasonEntity);
            season.setDriver(driver.getFullName());
            int totalpoints = getDriverChampionshipPoints(season);
            totalpoints = totalpoints + getDriverQualifyPoints(eventDTO, driver);
            season.setSeasonPoints(totalpoints);
            seasonResultRepository.save(season);
        }
    }

    private void updateRacePoints(EventDTO eventDTO, List<PlayerEntity> driversForRace1, SeasonEntity seasonEntity) {
        for (PlayerEntity driver:driversForRace1) {
            SeasonResultEntity season = getUsedOrNewSeason(driver, eventDTO);
            season.setSeason(seasonEntity);
            season.setDriver(driver.getFullName());
            season.setSeasonPoints(getTotalpoints(driver, season));
            season.setCarname(driver.getCar());
            seasonResultRepository.save(season);
        }
    }

    private int getTotalpoints(PlayerEntity driver, SeasonResultEntity season) {
        int totalpoints = getDriverChampionshipPoints(season);

        if (!checkCarUsedBefore(driver, season)){
            log.info("driver: " + driver.getFullName() + " changed from car: " + season.getCarname() + " to " + driver.getCar());
            log.info("so total points of: " + totalpoints + " are lost!");
            totalpoints = 0;
        }
        totalpoints = totalpoints + driver.getPoints();
        return totalpoints;
    }

    private boolean checkCarUsedBefore(PlayerEntity driver, SeasonResultEntity season) {
        return driver.getCar().equals(season.getCarname()) || season.getCarname() == null;
    }

    private SeasonResultEntity getUsedOrNewSeason(PlayerEntity driver, EventDTO eventDTO) {
        SeasonResultEntity season = seasonResultRepository.getSeasonByDriverAndSeasonName(driver.getFullName(), eventDTO.getServer());
        if (season == null) {
            season = new SeasonResultEntity();
        }
        return season;
    }

    private int getDriverChampionshipPoints(SeasonResultEntity season) {
        return season == null ?
                0 : season.getSeasonPoints();
    }

    private List<PlayerEntity> getDriversForRace(EventDTO eventDTO, SessionDTO session) {
        return playerRepository.getPlayersByEventIdAndSessionTypeOrderByPositionAsc(
                eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                        eventDTO.getServer(),
                        eventDTO.getTrack(),
                        eventDTO.getTrackLayout()).getId(),
                session.getType());
    }

}
