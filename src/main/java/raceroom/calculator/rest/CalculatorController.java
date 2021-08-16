package raceroom.calculator.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import raceroom.calculator.core.*;
import raceroom.calculator.repositories.EventResultEntity;
import raceroom.calculator.repositories.SeasonEntity;

import java.util.List;

@Slf4j
@RestController
public class CalculatorController {

    @Autowired
    private EventFactory eventFactory;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PlayerResultFactory playerResultFactory;

    @Autowired
    private SeasonFactory seasonFactory;

    @Autowired
    private QualifyFactory qualifyFactory;

    @Autowired
    private RaceService raceService;

    @Autowired
    FastestLapFactory fastestLapFactory;

    @PostMapping(value="/calculateEvent")
    public String post(@RequestBody EventDTO eventDTO) {
        eventDTO.setServer(getShortServername(eventDTO.getServer()));
        SeasonEntity seasonEntity = seasonFactory.seasonBuilder(eventDTO);
        eventFactory.eventBuilder(eventDTO, seasonEntity);
        sessionFactory.sessionBuilder(eventDTO);
        playerResultFactory.playerResultsBuilder(eventDTO);
        eventFactory.calculateEventResults(eventDTO);
        seasonFactory.seasonResultsBuilder(eventDTO, seasonEntity);
        return "upload succes!";
    }

    @GetMapping(value = "/eventResults")
    public List<EventResultEntity> get(@RequestParam("eventname") String eventname) {
        return eventFactory.getEventresult(eventname);
    }

    private String getShortServername(String servername) {
        return servername.replace(": https://discord.gg/purXnnMgRA [twitch]", "");
    }

}
