package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.EventRepository;
import raceroom.calculator.repositories.PlayerEntity;
import raceroom.calculator.repositories.PlayerRepository;
import raceroom.calculator.rest.EventDTO;
import raceroom.calculator.rest.SessionDTO;

import java.util.List;

@Component
@Slf4j
public class FastestLapFactory {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EventRepository eventRepository;

    public void fastestLapBuilderRace1(EventDTO eventDTO) {
        for (SessionDTO sessionDTO : eventDTO.getSessions()) {
            if (sessionDTO.getType().equals("Race")) {
                Long eventId = eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                        eventDTO.getServer(),
                        eventDTO.getTrack(),
                        eventDTO.getTrackLayout()).getId();
                List<PlayerEntity> bestLapTimeAsc = playerRepository.getPlayersByEventIdAndSessionTypeOrderByBestLapTimeAsc(
                        eventId, sessionDTO.getType());
                PlayerEntity player = bestLapTimeAsc.get(0);
                player.setFastestLap(true);
                playerRepository.save(player);
            }
        }
        log.info("Fastest lap resuls have been set");
    }

    public void fastestLapBuilderRace2(EventDTO eventDTO) {
        for (SessionDTO sessionDTO : eventDTO.getSessions()) {
            if (sessionDTO.getType().equals("Race2")) {
                Long eventId = eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                        eventDTO.getServer(),
                        eventDTO.getTrack(),
                        eventDTO.getTrackLayout()).getId();
                List<PlayerEntity> bestLapTimeAsc = playerRepository.getPlayersByEventIdAndSessionTypeOrderByBestLapTimeAsc(
                        eventId, sessionDTO.getType());
                PlayerEntity player = bestLapTimeAsc.get(0);
                player.setFastestLap(true);
                playerRepository.save(player);
            }
        }
        log.info("Fastest lap resuls have been set");
    }
}
