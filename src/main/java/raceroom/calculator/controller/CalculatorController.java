package raceroom.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raceroom.calculator.factories.PlayerFactory;
import raceroom.calculator.factories.RaceFactory;
import raceroom.calculator.factories.SessionFactory;
import raceroom.calculator.model.Player;
import raceroom.calculator.model.Session;
import raceroom.calculator.util.JsonRace;

import java.util.List;

@CrossOrigin
@RestController
public class CalculatorController {

    @Autowired
    private RaceFactory raceFactory;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PlayerFactory playerFactory;

    @PostMapping(value="/calculateRace")
    public List<Player> post(@RequestBody JsonRace jsonRace) {
        raceFactory.raceBuilder(jsonRace);
        sessionFactory.sessionBuilder(jsonRace);
        return playerFactory.playerBuilder(jsonRace);
    }
}
