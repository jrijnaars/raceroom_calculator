package raceroom.calculator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import raceroom.calculator.repositories.PlayerRepository;

@Controller
public class PlayersController {

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/players")
    public String players(Model model){
        model.addAttribute("players", playerRepository.findAll());
        return "players";
    }
}
