package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.model.Player;
import raceroom.calculator.repositories.PlayerRepository;
import raceroom.calculator.repositories.RaceRepository;
import raceroom.calculator.rest.PlayerDTO;
import raceroom.calculator.rest.RaceDTO;
import raceroom.calculator.rest.SessionDTO;

@Slf4j
@Component
public class PlayerFactory {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RaceRepository raceRepository;

    public void playerBuilder(RaceDTO raceDTO) {
        for (SessionDTO sessionDTO : raceDTO.getSessions()) {
            for (PlayerDTO playerDTO : sessionDTO.getPlayerDTOS()) {
                Player player = createPlayer(playerDTO, raceDTO, sessionDTO);
                playerRepository.save(player);

            }
            log.info("Players in session {} are saved in the database", sessionDTO.getType());
        }
    }

    private Player createPlayer(PlayerDTO playerDTO, RaceDTO raceDTO, SessionDTO sessionDTO) {
        Player player = new Player();
        player.setRaceId(raceRepository.getRaceByServerAndTrackAndTrackLayout(
                raceDTO.getServer(),
                raceDTO.getTrack(),
                raceDTO.getTrackLayout()).getId());
        player.setSessionType(sessionDTO.getType());
        player.setUserId(playerDTO.getUserId());
        player.setFullName(playerDTO.getFullName());
        player.setUsername(playerDTO.getUsername());
        player.setUserWeightPenalty(playerDTO.getUserWeightPenalty());
        player.setCarId(playerDTO.getCarId());
        player.setCar(playerDTO.getCar());
        player.setCarWeightPenalty(playerDTO.getCarWeightPenalty());
        player.setPosition(playerDTO.getPosition());
        player.setPositionInClass(playerDTO.getPositionInClass());
        player.setStartPosition(playerDTO.getStartPosition());
        player.setStartPositionInClass(playerDTO.getStartPositionInClass());
        player.setFinishStatus(playerDTO.getFinishStatus());
        return player;
    }
}
