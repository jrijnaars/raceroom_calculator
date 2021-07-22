package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.EventEntity;
import raceroom.calculator.repositories.EventRepository;
import raceroom.calculator.repositories.SessionEntity;
import raceroom.calculator.repositories.SessionRepository;
import raceroom.calculator.rest.EventDTO;
import raceroom.calculator.rest.SessionDTO;

@Slf4j
@Component
public class SessionFactory extends CalculatorFactory {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private EventRepository eventRepository;

    public void sessionBuilder(EventDTO eventDTO) {
        for (SessionDTO sessionDTO : eventDTO.getSessions()) {
            createSession(sessionDTO, eventDTO);
        }
    }

    private void createSession(SessionDTO sessionDTO, EventDTO eventDTO) {
        EventEntity eventEntity = eventRepository.getEventEntityByServerAndTrackAndTrackLayout(getShortServername(eventDTO.getServer()),
                eventDTO.getTrack(),
                eventDTO.getTrackLayout());
        SessionEntity session = new SessionEntity();
        session.setType(sessionDTO.getType());
        session.setEventname(eventEntity.getEventName());
        session.setEventId(eventEntity.getId());
        sessionRepository.save(session);
        log.info("Session {} is saved in the database", session.getType());
    }
}
