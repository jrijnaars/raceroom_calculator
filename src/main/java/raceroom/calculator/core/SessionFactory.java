package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.*;
import raceroom.calculator.rest.EventDTO;
import raceroom.calculator.rest.PlayerDTO;
import raceroom.calculator.rest.SessionDTO;

@Slf4j
@Component
public class SessionFactory extends CalculatorFactory {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PlayerResultFactory playerResultFactory;

    public void sessionBuilder(EventDTO eventDTO, EventEntity eventEntity) {
        for (SessionDTO sessionDTO : eventDTO.getSessions()) {
            SessionEntity sessionEntity = createSession(sessionDTO, eventEntity);
            for (PlayerDTO player: sessionDTO.getPlayerDTOS()) {
                PlayerResultEntity playerResult = playerResultFactory.playerResultsBuilder(player, sessionEntity, eventEntity);
                sessionEntity.getPlayerResults().add(playerResult);
            }
        }
    }

    private SessionEntity createSession(SessionDTO sessionDTO, EventEntity eventEntity) {
        SessionEntity session = new SessionEntity();
        session.setType(sessionDTO.getType());
        session.setEvent(eventEntity);
        eventEntity.getSessions().add(session);
        log.info("Session {} is added to the event", session.getType());
        return session;
    }
}
