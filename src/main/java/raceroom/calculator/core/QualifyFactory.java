package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.PlayerEntity;
import raceroom.calculator.repositories.PlayerRepository;
import raceroom.calculator.repositories.RaceRepository;
import raceroom.calculator.rest.PlayerDTO;
import raceroom.calculator.rest.RaceDTO;
import raceroom.calculator.rest.SessionDTO;

@Component
@Slf4j
public class QualifyFactory {

    @Autowired
    RaceRepository raceRepository;

    @Autowired
    PlayerRepository playerRepository;

    public void qualifyBuilder(RaceDTO raceDTO) {
        for (SessionDTO sessionDTO : raceDTO.getSessions()) {
            for (PlayerDTO playerDTO : sessionDTO.getPlayerDTOS()) {
                if (sessionDTO.getType().equals("Qualify")){
                    PlayerEntity playerEntity =  playerRepository.getPlayerEntityByRaceIdAndSessionTypeAndFullName(
                            raceRepository.getRaceByServerAndTrackAndTrackLayout(
                                    raceDTO.getServer(),
                                    raceDTO.getTrack(),
                                    raceDTO.getTrackLayout()).getId(), "Qualify", playerDTO.getFullName());
                    setQualifyPoints(sessionDTO, playerEntity);
                    playerRepository.save(playerEntity);
                }
            }
        }
        log.info("qualifying results have been set");
    }

    private void setQualifyPoints(SessionDTO sessionDTO, PlayerEntity playerEntity) {
        if (sessionDTO.getType().equals("Qualify")) {
            if (playerEntity.getPosition() == 1) {
                playerEntity.setPoints(6);
            }
        }
    }
}
