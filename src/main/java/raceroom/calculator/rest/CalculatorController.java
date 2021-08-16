package raceroom.calculator.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import raceroom.calculator.core.EventFactory;
import raceroom.calculator.core.FastestLapFactory;
import raceroom.calculator.core.SeasonFactory;
import raceroom.calculator.repositories.*;

import java.util.List;

@Slf4j
@RestController
public class CalculatorController {

    @Autowired
    private EventFactory eventFactory;

    @Autowired
    private SeasonFactory seasonFactory;

    @Autowired
    FastestLapFactory fastestLapFactory;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping(value="/calculateEvent")
    public String post(@RequestBody EventDTO eventDTO) {
        eventDTO.setServer(getShortServername(eventDTO.getServer()));
        SeasonEntity seasonEntity = seasonFactory.seasonBuilder(eventDTO);
        seasonRepository.save(seasonEntity);
        EventEntity eventEntity = eventFactory.eventBuilder(eventDTO, seasonEntity);
        eventRepository.save(eventEntity);
        eventFactory.calculateEventResults(eventEntity);
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
