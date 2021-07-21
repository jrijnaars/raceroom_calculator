package raceroom.calculator.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import raceroom.calculator.repositories.EventEntity;
import raceroom.calculator.repositories.EventRepository;
import raceroom.calculator.repositories.EventResultsRepository;
import raceroom.calculator.repositories.PlayerEntity;
import raceroom.calculator.repositories.PlayerRepository;
import raceroom.calculator.repositories.SessionRepository;

@Controller
public class EventsController {

    @Autowired
    private EventRepository eventRepository;
    @Autowired 
    private EventResultsRepository eventResultsRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private PlayerRepository playerRepository;
    
    @GetMapping("/events")
    public String events(Model model){
        model.addAttribute("events", eventRepository.findAll());
        return "events";
    }

    @GetMapping("/events/{id}")
    public String eventDetail(@PathVariable("id") Long id, Model model){
        Optional<EventEntity> event = eventRepository.findById(id);
        if(event.isEmpty()){
            throw new IllegalArgumentException("No event with this id!");
        }

        model.addAttribute("event", event.get());
        //TODO search on event id, name, or whatever
        model.addAttribute("results", eventResultsRepository.findAll());
        model.addAttribute("sessions", sessionRepository.getSessionsByEventId(id));
        return "eventDetail";
    }

    @GetMapping("/events/{id}/{sessionType}")
    public String eventSessionDetail(@PathVariable("id") Long id, @PathVariable("sessionType") String sessionType, Model model){
        List<PlayerEntity> players = playerRepository.getPlayersByEventIdAndSessionTypeOrderByPositionAsc(id, sessionType);
        model.addAttribute("eventId", id);
        model.addAttribute("sessionType", sessionType);
        model.addAttribute("players", players);
        model.addAttribute("nbPlayers", players.size());
        return "eventSessionDetail";
    }
}
