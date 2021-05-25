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
import java.util.stream.Collectors;

@Component
@Slf4j
public class RaceFactory {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EventRepository eventRepository;

    public void raceBuilder(EventDTO eventDTO) {
        List<SessionDTO> racesInEvent = getRacesInEvent(eventDTO);
        for (SessionDTO sessionDTO : racesInEvent) {
            setRacepointsForSession(eventDTO, sessionDTO);
        }
        log.info("Raceresults have been set");
    }

    private void setRacepointsForSession(EventDTO eventDTO, SessionDTO sessionDTO) {
        for (PlayerDTO playerDTO : sessionDTO.getPlayerDTOS()) {
            PlayerEntity playerEntity = playerRepository.getPlayerEntityByEventIdAndSessionTypeAndFullName(
                    eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                            eventDTO.getServer(),
                            eventDTO.getTrack(),
                            eventDTO.getTrackLayout()).getId(), sessionDTO.getType(), playerDTO.getFullName());
            setRacePoints(playerEntity);
            playerRepository.save(playerEntity);
        }
    }

    private void setRacePoints(PlayerEntity playerEntity) {
        setPointsByRacePosition(playerEntity);
        excludeDidNotFinish(playerEntity);
        excludeDisqualified(playerEntity);
    }

    private void excludeDisqualified(PlayerEntity playerEntity) {
        if (playerEntity.getFinishStatus().equals("Disqualified")) {
            playerEntity.setPoints(0);
        }
    }

    private void excludeDidNotFinish(PlayerEntity playerEntity) {
        if (playerEntity.getFinishStatus().equals("DidNotFinish")) {
            playerEntity.setPoints(0);
        }
    }

    private void setPointsByRacePosition(PlayerEntity playerEntity) {
        switch (playerEntity.getPosition()) {
            case 1:
                playerEntity.setPoints(50);
                break;
            case 2:
                playerEntity.setPoints(44);
                break;
            case 3:
                playerEntity.setPoints(40);
                break;
            case 4:
                playerEntity.setPoints(37);
                break;
            case 5:
                playerEntity.setPoints(34);
                break;
            case 6:
                playerEntity.setPoints(32);
                break;
            case 7:
                playerEntity.setPoints(30);
                break;
            case 8:
                playerEntity.setPoints(28);
                break;
            case 9:
                playerEntity.setPoints(26);
                break;
            case 10:
                playerEntity.setPoints(25);
                break;
            case 11:
                playerEntity.setPoints(24);
                break;
            case 12:
                playerEntity.setPoints(23);
                break;
            case 13:
                playerEntity.setPoints(22);
                break;
            case 14:
                playerEntity.setPoints(21);
                break;
            case 15:
                playerEntity.setPoints(20);
                break;
            case 16:
                playerEntity.setPoints(19);
                break;
            case 17:
                playerEntity.setPoints(18);
                break;
            case 18:
                playerEntity.setPoints(17);
                break;
            case 19:
                playerEntity.setPoints(16);
                break;
            default:
                playerEntity.setPoints(15);
                break;
        }

    }

    public List<SessionDTO> getRacesInEvent(EventDTO eventDTO) {
        return eventDTO.getSessions().stream().filter(session -> session.getType().contains("Race")).collect(Collectors.toList());
    }
}
