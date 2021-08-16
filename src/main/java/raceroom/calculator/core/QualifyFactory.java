package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.EventRepository;
import raceroom.calculator.repositories.PlayerResultRepository;

@Component
@Slf4j
public class QualifyFactory extends CalculatorFactory {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    PlayerResultRepository playerResultRepository;

//    public void qualifyBuilder(EventDTO eventDTO) {
//        for (SessionDTO sessionDTO : eventDTO.getSessions()) {
//            for (PlayerDTO playerDTO : sessionDTO.getPlayerDTOS()) {
//                if (sessionDTO.getType().equals("Qualify")){
//                    PlayerResultEntity playerResultEntity =  playerResultRepository.getPlayerEntityByEventIdAndSessionTypeAndFullName(
//                            eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
//                                    eventDTO.getServer(),
//                                    eventDTO.getTrack(),
//                                    eventDTO.getTrackLayout()).getId(), "Qualify", playerDTO.getFullName());
//                    setQualifyPoints(sessionDTO, playerResultEntity);
//                    playerResultRepository.save(playerResultEntity);
//                }
//            }
//        }
//        log.info("qualifying results have been set");
//    }


}
