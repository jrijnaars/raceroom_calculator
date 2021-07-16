package raceroom.calculator.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import raceroom.calculator.repositories.EventEntity;
import raceroom.calculator.repositories.EventRepository;

@Controller
public class EventsController {

    @Autowired
    private EventRepository eventRepository;
    
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
        return "eventDetail";
    }
}
