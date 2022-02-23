package raceroom.calculator.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import raceroom.calculator.core.*;
import raceroom.calculator.repositories.EventResultEntity;

import java.util.List;

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

    @Autowired
    CarDataProcessor carDataProcessor;

    @PostMapping(value="/calculateEvent")
    public String post(@RequestBody EventDTO eventDTO) {
        eventDTO.setServer(getShortServername(eventDTO.getServer()));
        eventFactory.eventBuilder(eventDTO);
        sessionFactory.sessionBuilder(eventDTO);
        playerFactory.playerBuilder(eventDTO);
        qualifyFactory.qualifyBuilder(eventDTO);
        raceFactory.raceBuilder(eventDTO);
        eventFactory.calculateEventResults(eventDTO);
        seasonFactory.seasonBuilder(eventDTO);
        return "upload succes!";
    }

    @GetMapping(value = "/eventResults")
    public List<EventResultEntity> get(@RequestParam("eventname") String eventname) {
        return eventFactory.getEventresult(eventname);
    }

    @PostMapping(value= "/ProcessCarData")
    public String processCarData(@RequestBody CarDataDTO carDataDTO) {
        carDataProcessor.insertCardataIntoDB(carDataDTO);
        return "Car is updated in the DB";
    }

    @PutMapping(value= "/processAllCars")
    public String processCarData() {
        carDataProcessor.getCarDataFromRecources();
        return "all cars are updated in the database";
    }

    private String getShortServername(String servername) {
        return servername.replace(": https://discord.gg/purXnnMgRA [twitch]", "");
    }
}
