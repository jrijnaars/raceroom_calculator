package raceroom.calculator.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.model.EventEntity;
import raceroom.calculator.repositories.EventRepository;
import raceroom.calculator.rest.EventDTO;

@Slf4j
@Component
public class EventFactory {

    @Autowired
    private EventRepository eventRepository;

    public void eventBuilder(EventDTO eventDTO) {
        eventdataDuplicateCheck(eventDTO);
        EventEntity eventEntity = createEvent(eventDTO);
        eventRepository.save(eventEntity);
        log.info("Event {} is uploaded in the database", eventEntity.getEventName());
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

    private EventEntity createEvent(EventDTO eventDTO) {
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
        eventEntity.setEventName(createEventname(eventDTO));
        return eventEntity;
    }

    private String createEventname(EventDTO eventDTO) {
        return eventDTO.getServer() + "_" + eventDTO.getTrack() + "_" + eventDTO.getTrackLayout();
    }
}
