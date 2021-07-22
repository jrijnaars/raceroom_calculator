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

@Component
@Slf4j
public class QualifyFactory extends CalculatorFactory {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    PlayerRepository playerRepository;

    public void qualifyBuilder(EventDTO eventDTO) {
        for (SessionDTO sessionDTO : eventDTO.getSessions()) {
            for (PlayerDTO playerDTO : sessionDTO.getPlayerDTOS()) {
                if (sessionDTO.getType().equals("Qualify")){
                    PlayerEntity playerEntity =  playerRepository.getPlayerEntityByEventIdAndSessionTypeAndFullName(
                            eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                                    getShortServername(eventDTO.getServer()),
                                    eventDTO.getTrack(),
                                    eventDTO.getTrackLayout()).getId(), "Qualify", playerDTO.getFullName());
                    setQualifyPoints(sessionDTO, playerEntity);
                    playerRepository.save(playerEntity);
                }
            }
        }
        log.info("qualifying results have been set");
    }

    private void setQualifyPoints(SessionDTO sessionDTO, PlayerEntity playerEntity) {
        if (sessionDTO.getType().equals("Qualify")) {
            if (playerEntity.getPosition() == 1) {
                playerEntity.setPoints(6);
            }
        }
    }
}
