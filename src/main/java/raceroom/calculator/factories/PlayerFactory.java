package raceroom.calculator.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.model.Player;
import raceroom.calculator.repositories.PlayerRepository;
import raceroom.calculator.repositories.RaceRepository;
import raceroom.calculator.repositories.SessionRepository;
import raceroom.calculator.util.JsonPlayer;
import raceroom.calculator.util.JsonRace;
import raceroom.calculator.util.JsonSession;

import java.util.List;

@Component
public class PlayerFactory {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RaceRepository raceRepository;

    public List<Player> playerBuilder(JsonRace jsonRace) {
        for (JsonSession jsonSession : jsonRace.getSessions()) {
            for (JsonPlayer jsonPlayer : jsonSession.getJsonPlayers()) {
                Player player = createPlayer(jsonPlayer, jsonRace, jsonSession);
                playerRepository.save(player);
            }
        }
        return playerRepository.getPlayersByRaceIdAndAndSessionTypeOrderByPositionAsc(
                raceRepository.getRaceByServerAndTrackAndTrackLayout(jsonRace.getServer(), jsonRace.getTrack(), jsonRace.getTrackLayout()).getId(),
                "Race");
    }

    private Player createPlayer(JsonPlayer jsonPlayer, JsonRace jsonRace, JsonSession jsonSession) {
        Player player = new Player();
        player.setRaceId(raceRepository.getRaceByServerAndTrackAndTrackLayout(
                jsonRace.getServer(),
                jsonRace.getTrack(),
                jsonRace.getTrackLayout()).getId());
        player.setSessionType(jsonSession.getType());
        player.setUserId(jsonPlayer.getUserId());
        player.setFullName(jsonPlayer.getFullName());
        player.setUsername(jsonPlayer.getUsername());
        player.setUserWeightPenalty(jsonPlayer.getUserWeightPenalty());
        player.setCarId(jsonPlayer.getCarId());
        player.setCar(jsonPlayer.getCar());
        player.setCarWeightPenalty(jsonPlayer.getCarWeightPenalty());
        player.setPosition(jsonPlayer.getPosition());
        player.setPositionInClass(jsonPlayer.getPositionInClass());
        player.setStartPosition(jsonPlayer.getStartPosition());
        player.setStartPositionInClass(jsonPlayer.getStartPositionInClass());
        player.setFinishStatus(jsonPlayer.getFinishStatus());
        return player;
    }
}
