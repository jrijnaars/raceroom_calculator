package raceroom.calculator.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.*;
import raceroom.calculator.rest.EventDTO;
import raceroom.calculator.rest.SessionDTO;

import java.util.List;

@Slf4j
@Component
public class EventFactory extends CalculatorFactory{

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventResultsRepository eventResultsRepository;

    @Autowired
    private PlayerResultRepository playerResultRepository;

    public void eventBuilder(EventDTO eventDTO, SeasonEntity seasonEntity) {
        eventdataDuplicateCheck(eventDTO);
        EventEntity eventEntity = createEvent(eventDTO, seasonEntity);
        eventRepository.save(eventEntity);
        log.info("Event {} is uploaded in the database", eventEntity.getEventName());
    }

    public void calculateEventResults(EventDTO eventDTO) {
        EventEntity eventEntity = eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                eventDTO.getServer(),
                eventDTO.getTrack(),
                eventDTO.getTrackLayout());
        List<SessionDTO> racesInEvent = getRacesInEvent(eventDTO);
        for (SessionDTO race: racesInEvent) {
            List<PlayerResultEntity> playersResults = playerResultRepository.getPlayersByEventIdAndSessionTypeOrderByPositionAsc(eventEntity.getId(), race.getType());
            createEventResultForPlayers(eventEntity, playersResults, eventDTO);
        }
    }

    @SneakyThrows
    private void eventdataDuplicateCheck(EventDTO eventDTO) {
        EventEntity existingEventEntity = eventRepository.getEventEntityByServerAndTrackAndTrackLayout(
                eventDTO.getServer(),
                eventDTO.getTrack(),
                eventDTO.getTrackLayout());
        if (existingEventEntity != null) {
            throw new Exception("Event data already uploaded");
        }
    }

    private EventEntity createEvent(EventDTO eventDTO, SeasonEntity seasonEntity) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setServer(eventDTO.getServer());
        eventEntity.setStartTime(eventDTO.getStartTime());
        eventEntity.setTime(eventDTO.getTime());
        eventEntity.setFuelUsage(eventDTO.getFuelUsage());
        eventEntity.setTireWear(eventDTO.getTireWear());
        eventEntity.setMechanicalDamage(eventDTO.getMechanicalDamage());
        eventEntity.setFlagRules(eventDTO.getFlagRules());
        eventEntity.setCutRules(eventDTO.getCutRules());
        eventEntity.setMandatoryPitstop(eventDTO.getMandatoryPitstop());
        eventEntity.setTrack(eventDTO.getTrack());
        eventEntity.setTrackLayout(eventDTO.getTrackLayout());
        eventEntity.setEventName(eventDTO.getServer() + "_" + eventDTO.getTrack() + "_" + eventDTO.getTrackLayout());
        eventEntity.setSeason(seasonEntity);
        return eventEntity;
    }

    private void createEventResultForPlayers(EventEntity eventEntity, List<PlayerResultEntity> playerResults, EventDTO eventDTO) {
        for (PlayerResultEntity playerResult: playerResults) {
            EventResultEntity eventResults = getNewOrUsedEvent(eventEntity, playerResult);
            eventResults.setEventId(eventEntity.getId());
            eventResults.setEventName(eventEntity.getEventName());
            eventResults.setPlayer(playerResult.getPlayer());
            eventResults.setCarName(playerResult.getCar());
            eventResults.setTrackName(eventEntity.getTrack());
            eventResults.setEventPoints(setEventPoints(eventResults, playerResult, eventDTO));
            eventResultsRepository.save(eventResults);
        }
    }

    private int setEventPoints(EventResultEntity eventResults, PlayerResultEntity player, EventDTO eventDTO) {
        int totalPoints = eventResults.getEventPoints() + player.getPoints();
        if (player.getSessionType().equals("Race")) {
            totalPoints = totalPoints + getDriverQualifyPoints(eventDTO, player);
        }
        return totalPoints;
    }

    private EventResultEntity getNewOrUsedEvent(EventEntity eventEntity, PlayerResultEntity playerResult) {
        EventResultEntity eventResults = eventResultsRepository.getEventResultEntityByEventNameAndPlayer(
                eventEntity.getEventName(),
                playerResult.getPlayer());
        if (eventResults == null) {
            return new EventResultEntity();
        } else {
            return eventResults;
        }
    }

    public List<EventResultEntity> getEventresult(String eventname) {
        return eventResultsRepository.getEventResultEntitiesByEventNameOrderByEventPointsDesc(eventname);
    }
}
