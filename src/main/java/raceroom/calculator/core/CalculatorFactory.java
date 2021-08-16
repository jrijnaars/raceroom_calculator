package raceroom.calculator.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.EventRepository;
import raceroom.calculator.repositories.PlayerResultEntity;
import raceroom.calculator.repositories.PlayerResultRepository;
import raceroom.calculator.rest.EventDTO;
import raceroom.calculator.rest.SessionDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CalculatorFactory {

    @Autowired
    private PlayerResultRepository playerResultRepository;

    @Autowired
    private EventRepository eventRepository;

    protected int getDriverQualifyPoints(EventDTO eventDTO, PlayerResultEntity driver) {
        return playerResultRepository.getPlayerEntityByEventIdAndSessionTypeAndPlayer(
                eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                        eventDTO.getServer(),
                        eventDTO.getTrack(),
                        eventDTO.getTrackLayout()).getId(), "Qualify", driver.getPlayer()).getPoints();
    }

    protected List<SessionDTO> getRacesInEvent(EventDTO eventDTO) {
        return eventDTO.getSessions().stream().filter(session -> session.getType().contains("Race")).collect(Collectors.toList());
    }


}
