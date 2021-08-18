package raceroom.calculator.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.*;
import raceroom.calculator.rest.EventDTO;

@Slf4j
@Component
public class EventFactory extends CalculatorFactory {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventResultsRepository eventResultsRepository;

    @Autowired
    private SessionFactory sessionFactory;

    public EventEntity eventBuilder(EventDTO eventDTO, SeasonEntity seasonEntity) {
        eventdataDuplicateCheck(eventDTO);
        EventEntity eventEntity = createEvent(eventDTO, seasonEntity);
        eventRepository.save(eventEntity);
        log.info("Event {} is uploaded in the database", eventEntity.getEventName());
        return eventEntity;
    }

    public void calculateEventResults(EventEntity eventEntity) {
        for (SessionEntity session: eventEntity.getSessions()) {
            createEventResultForPlayers(session);
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
        sessionFactory.sessionBuilder(eventDTO, eventEntity);

        return eventEntity;
    }

    private void createEventResultForPlayers(SessionEntity sessionEntity) {
        EventEntity eventEntity = sessionEntity.getEvent();
        for (PlayerResultEntity playerResult : sessionEntity.getPlayerResults()) {
            EventResultEntity eventResults = getNewOrUsedEvent(sessionEntity, playerResult);
            eventResults.setEvent(eventEntity);
            eventEntity.getEventResults().add(eventResults);
            eventResults.setPlayer(playerResult.getPlayer());
            eventResults.setCarName(playerResult.getCar());
            eventResults.setTrackName(eventEntity.getTrack());
            eventResults.setEventPoints(setEventPoints(eventResults, playerResult));
            eventResultsRepository.save(eventResults);
        }
    }

    private int setEventPoints(EventResultEntity eventResults, PlayerResultEntity playerResult) {
        int totalPoints = eventResults.getEventPoints() + playerResult.getPoints();
        if (playerResult.getSession().getType().equals("Qualify")) {
            totalPoints = totalPoints + getDriverQualifyPoints(playerResult);
        }
        return totalPoints;
    }

    private EventResultEntity getNewOrUsedEvent(SessionEntity sessionEntity, PlayerResultEntity playerResult) {
        EventResultEntity eventResults = eventResultsRepository.getEventResultEntityByEvent_EventNameAndPlayer(
                sessionEntity.getEvent().getEventName(),
                playerResult.getPlayer());
        if (eventResults == null) {
            return new EventResultEntity();
        } else {
            return eventResults;
        }
    }

}
