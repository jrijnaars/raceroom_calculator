package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.*;
import raceroom.calculator.rest.EventDTO;
import raceroom.calculator.rest.PlayerDTO;
import raceroom.calculator.rest.SessionDTO;

@Slf4j
@Component
public class PlayerResultFactory extends CalculatorFactory {

    @Autowired
    private PlayerResultRepository playerResultRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RaceService raceService;

    public void playerResultsBuilder(EventDTO eventDTO) {
        for (SessionDTO sessionDTO : eventDTO.getSessions()) {
            for (PlayerDTO playerDTO : sessionDTO.getPlayerDTOS()) {
                PlayerResultEntity playerResultEntity = createPlayerResult(playerDTO, eventDTO, sessionDTO);
                playerResultRepository.save(playerResultEntity);
            }
            log.info("Players in session {} are saved in the database", sessionDTO.getType());
        }
    }


    private PlayerResultEntity createPlayerResult(PlayerDTO playerDTO, EventDTO eventDTO, SessionDTO sessionDTO) {
        PlayerResultEntity playerResultEntity = new PlayerResultEntity();
        playerResultEntity.setEventId(eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                eventDTO.getServer(),
                eventDTO.getTrack(),
                eventDTO.getTrackLayout()).getId());
        playerResultEntity.setSessionType(sessionDTO.getType());
        PlayerEntity playerEntity = playerRepository.findById(playerDTO.getUserId()).orElse(
                new PlayerEntity(playerDTO.getUserId(), playerDTO.getFullName(), playerDTO.getUsername()));
        playerRepository.save(playerEntity);
        playerResultEntity.setPlayer(playerEntity);
        playerResultEntity.setUserWeightPenalty(playerDTO.getUserWeightPenalty());
        playerResultEntity.setCarId(playerDTO.getCarId());
        playerResultEntity.setCar(playerDTO.getCar());
        playerResultEntity.setCarWeightPenalty(playerDTO.getCarWeightPenalty());
        playerResultEntity.setPosition(playerDTO.getPosition());
        playerResultEntity.setPositionInClass(playerDTO.getPositionInClass());
        playerResultEntity.setStartPosition(playerDTO.getStartPosition());
        playerResultEntity.setStartPositionInClass(playerDTO.getStartPositionInClass());
        playerResultEntity.setBestLapTime(playerDTO.getBestLapTime());
        playerResultEntity.setFinishStatus(playerDTO.getFinishStatus());
        raceService.setQualifyPoints(playerResultEntity);
        playerResultEntity.setIncidentPoints(raceService.calculateIncidentPoints(playerDTO, playerResultEntity.getSessionType()));
        raceService.setRacePoints(playerResultEntity);
        return playerResultEntity;
    }


}
