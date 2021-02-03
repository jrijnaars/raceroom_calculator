package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.EventRepository;
import raceroom.calculator.repositories.PlayerEntity;
import raceroom.calculator.repositories.PlayerRepository;
import raceroom.calculator.rest.EventDTO;
import raceroom.calculator.rest.PlayerDTO;
import raceroom.calculator.rest.SessionDTO;

import java.util.List;

@Slf4j
@Component
public class PlayerFactory {

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
//            setFastestLapPoints(sessionDTO, raceDTO);
            log.info("Players in session {} are saved in the database", sessionDTO.getType());
        }
    }

    private void setFastestLapPoints(SessionDTO sessionDTO, EventDTO eventDTO) {
        if (sessionDTO.getType().equals("Race")) {
            Long eventId = eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                    eventDTO.getServer(),
                    eventDTO.getTrack(),
                    eventDTO.getTrackLayout()).getId();
            List<PlayerEntity> bestLapTimeAsc = playerRepository.getPlayersByEventIdAndSessionTypeOrderByBestLapTimeAsc(
                    eventId, sessionDTO.getType());
            PlayerEntity player = bestLapTimeAsc.get(0);
            player.setPoints(player.getPoints() + 6);
            playerRepository.save(player);
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
        return playerEntity;
    }
}
