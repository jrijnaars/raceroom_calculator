package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.*;
import raceroom.calculator.rest.PlayerDTO;

@Slf4j
@Component
public class PlayerResultFactory extends CalculatorFactory {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RaceService raceService;

    public PlayerResultEntity playerResultsBuilder(PlayerDTO player, SessionEntity sessionEntity, EventEntity eventEntity) {

        log.info("Players in session {} are added", sessionEntity.getType());
        return createPlayerResult(player, sessionEntity, eventEntity);
    }


    private PlayerResultEntity createPlayerResult(PlayerDTO playerDTO, SessionEntity sessionEntity, EventEntity eventEntity) {
        PlayerResultEntity playerResultEntity = new PlayerResultEntity();
        playerResultEntity.setEvent(eventEntity);
        playerResultEntity.setSession(sessionEntity);
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
        playerResultEntity.setIncidentPoints(raceService.calculateIncidentPoints(playerDTO, sessionEntity.getType()));
        raceService.setRacePoints(playerResultEntity);
        return playerResultEntity;
    }


}
