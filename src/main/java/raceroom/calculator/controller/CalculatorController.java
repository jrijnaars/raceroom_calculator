package raceroom.calculator.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raceroom.calculator.factories.RaceFactory;
import raceroom.calculator.model.Race;
import raceroom.calculator.util.JsonRace;

@CrossOrigin
@RestController
public class CalculatorController {

    private RaceFactory raceFactory;


    public CalculatorController(RaceFactory raceFactory) {
        this.raceFactory = raceFactory;
    }

    @PostMapping(value="/calculateRace")
    public Race post(@RequestBody JsonRace jsonRace) {
        return raceFactory.raceBuilder(jsonRace);
    }
}
