package raceroom.calculator.rest;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import raceroom.calculator.core.EventFactory;
import raceroom.calculator.core.SeasonFactory;
import raceroom.calculator.repositories.*;

@Slf4j
@RestController
public class CalculatorController {

    @Autowired
    private EventFactory eventFactory;

    @Autowired
    private SeasonFactory seasonFactory;

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

    @GetMapping(value = "/seasons")
    public Iterable<SeasonEntity> getSeasons() {
        return seasonRepository.findAll();
    }

    @GetMapping(value = "/season/{id}")
    public SeasonSummaryDTO getSeason(Model Model, @PathVariable("id") SeasonEntity seasonEntity) {
        seasonRepository.findAll();
        ModelMapper modelmapper = new ModelMapper();
        modelmapper.typeMap(SeasonEntity.class, SeasonSummaryDTO.class).addMappings(mapper -> {
            mapper.map(SeasonEntity::getsortedResults, SeasonSummaryDTO::setResults);
        });
        return modelmapper.map(seasonEntity, SeasonSummaryDTO.class);
    }

    @GetMapping(value = "/event/{id}")
    public EventSummaryDTO getEvent(Model Model, @PathVariable("id") EventResultEntity eventResultEntity) {
        eventRepository.findAll();
        ModelMapper modelmapper = new ModelMapper();
        modelmapper.typeMap(EventEntity.class, SeasonSummaryDTO.class).addMappings(mapper -> {
            mapper.map(EventEntity::getsortedResults, SeasonSummaryDTO::setResults);
        });
        return modelmapper.map(eventResultEntity, EventSummaryDTO.class);
    }

    private String getShortServername(String servername) {
        return servername.replace(": https://discord.gg/purXnnMgRA [twitch]", "");
    }


}
