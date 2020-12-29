package raceroom.calculator.factories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.model.Race;
import raceroom.calculator.model.Session;
import raceroom.calculator.repositories.RaceRepository;
import raceroom.calculator.repositories.SessionRepository;
import raceroom.calculator.util.JsonRace;
import raceroom.calculator.util.JsonSession;

@Slf4j
@Component
public class SessionFactory {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RaceRepository raceRepository;

    public void sessionBuilder(JsonRace jsonRace) {
        for (JsonSession jsonSession:jsonRace.getSessions()) {
            createSession(jsonSession, jsonRace);
        }
    }

    private void createSession(JsonSession jsonSession, JsonRace jsonRace) {
        Race race = raceRepository.getRaceByServerAndTrackAndTrackLayout(jsonRace.getServer(),
                jsonRace.getTrack(),
                jsonRace.getTrackLayout());
        Session session = new Session();
        session.setType(jsonSession.getType());
        session.setRacename(race.getRaceName());
        session.setRaceId(race.getId());
        sessionRepository.save(session);
        log.info("Session {} is saved in the database", session.getType());
    }


}
