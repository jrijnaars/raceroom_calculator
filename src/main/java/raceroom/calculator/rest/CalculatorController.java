package raceroom.calculator.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raceroom.calculator.core.*;

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

    @PostMapping(value="/calculateEvent")
    public String post(@RequestBody EventDTO eventDTO) {
        eventFactory.eventBuilder(eventDTO);
        sessionFactory.sessionBuilder(eventDTO);
        playerFactory.playerBuilder(eventDTO);
        qualifyFactory.qualifyBuilder(eventDTO);
        seasonFactory.seasonBuilder(eventDTO);
        return "upload succes!";
    }
}
