package raceroom.calculator.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raceroom.calculator.core.*;

@RestController
public class CalculatorController {

    @Autowired
    private RaceFactory raceFactory;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PlayerFactory playerFactory;

    @Autowired
    private SeasonFactory seasonFactory;

    @Autowired
    private QualifyFactory qualifyFactory;

    @PostMapping(value="/calculateRace")
    public String post(@RequestBody RaceDTO raceDTO) {
        raceFactory.raceBuilder(raceDTO);
        sessionFactory.sessionBuilder(raceDTO);
        playerFactory.playerBuilder(raceDTO);
        qualifyFactory.qualifyBuilder(raceDTO);
        seasonFactory.seasonBuilder(raceDTO);
        return "upload succes!";
    }
}
