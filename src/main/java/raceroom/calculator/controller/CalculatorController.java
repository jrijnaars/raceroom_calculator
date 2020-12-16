package raceroom.calculator.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raceroom.calculator.util.JsonRace;

@CrossOrigin
@RestController
public class CalculatorController {

    @PostMapping(value="/calculateRace")
    public JsonRace post(@RequestBody JsonRace jsonRace) throws Exception {
        return jsonRace;
    }
}
