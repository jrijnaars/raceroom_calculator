package raceroom.calculator.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CalculatorFactory {

    @Autowired
    private PlayerResultRepository playerResultRepository;

    @Autowired
    private EventRepository eventRepository;

    protected int getDriverQualifyPoints(PlayerResultEntity player) {
        return player.getSession().getType().equals("qualify") ? player.getPoints() : 0;
    }

    protected List<SessionEntity> getRacesInEvent(EventEntity eventEntity) {
        return eventEntity.getSessions().stream().filter(session -> session.getType().contains("Race")).collect(Collectors.toList());
    }


}
