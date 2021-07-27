package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.EventRepository;
import raceroom.calculator.repositories.PlayerEntity;
import raceroom.calculator.repositories.PlayerRepository;
import raceroom.calculator.rest.*;

@Slf4j
@Component
public class PlayerFactory extends CalculatorFactory{

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EventRepository eventRepository;

    public void playerBuilder(EventDTO eventDTO) {
        for (SessionDTO sessionDTO : eventDTO.getSessions()) {
            for (PlayerDTO playerDTO : sessionDTO.getPlayerDTOS()) {
                PlayerEntity playerEntity = createPlayer(playerDTO, eventDTO, sessionDTO);
                playerRepository.save(playerEntity);
            }
            log.info("Players in session {} are saved in the database", sessionDTO.getType());
        }
    }


    private PlayerEntity createPlayer(PlayerDTO playerDTO, EventDTO eventDTO, SessionDTO sessionDTO) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setEventId(eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                eventDTO.getServer(),
                eventDTO.getTrack(),
                eventDTO.getTrackLayout()).getId());
        playerEntity.setSessionType(sessionDTO.getType());
        playerEntity.setUserId(playerDTO.getUserId());
        playerEntity.setFullName(playerDTO.getFullName());
        playerEntity.setUsername(playerDTO.getUsername());
        playerEntity.setUserWeightPenalty(playerDTO.getUserWeightPenalty());
        playerEntity.setCarId(playerDTO.getCarId());
        playerEntity.setCar(playerDTO.getCar());
        playerEntity.setCarWeightPenalty(playerDTO.getCarWeightPenalty());
        playerEntity.setPosition(playerDTO.getPosition());
        playerEntity.setPositionInClass(playerDTO.getPositionInClass());
        playerEntity.setStartPosition(playerDTO.getStartPosition());
        playerEntity.setStartPositionInClass(playerDTO.getStartPositionInClass());
        playerEntity.setBestLapTime(playerDTO.getBestLapTime());
        playerEntity.setFinishStatus(playerDTO.getFinishStatus());
        if (sessionDTO.getType().equals("Race") || sessionDTO.getType().equals("Race2")) {
            playerEntity.setIncidentPoints(calculateIncidentPoints(playerDTO, sessionDTO));
        }
        return playerEntity;
    }

    private int calculateIncidentPoints(PlayerDTO playerDTO, SessionDTO sessionDTO) {
        int totalIncidentsPoint = 0;

        for (LapDTO lapDTO : playerDTO.getRaceSessionLaps()) {
            for (IncidentDTO incidentDTO : lapDTO.getIncident()) {
                totalIncidentsPoint = totalIncidentsPoint + incidentDTO.getPoints();
            }
        }
        return totalIncidentsPoint;
    }
}
