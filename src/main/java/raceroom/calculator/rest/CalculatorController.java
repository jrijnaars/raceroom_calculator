package raceroom.calculator.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raceroom.calculator.core.*;

@Slf4j
@RestController
public class CalculatorController {

    @Autowired
    private EventFactory eventFactory;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PlayerFactory playerFactory;

    @Autowired
    private SeasonFactory seasonFactory;

    @Autowired
    private QualifyFactory qualifyFactory;

    @Autowired
    private RaceFactory raceFactory;

    @Autowired
    FastestLapFactory fastestLapFactory;

    @PostMapping(value="/calculateEvent")
    public String post(@RequestBody EventDTO eventDTO) {
        eventFactory.eventBuilder(eventDTO);
        sessionFactory.sessionBuilder(eventDTO);
        playerFactory.playerBuilder(eventDTO);
        qualifyFactory.qualifyBuilder(eventDTO);
        raceFactory.raceBuilder(eventDTO);
        eventFactory.calculateEventResults(eventDTO);
        seasonFactory.seasonBuilder(eventDTO);
        return "upload succes!";
    }
}
