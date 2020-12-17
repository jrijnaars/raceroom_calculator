package raceroom.calculator.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.model.Session;
import raceroom.calculator.repositories.RaceRepository;
import raceroom.calculator.repositories.SessionRepository;
import raceroom.calculator.util.JsonRace;
import raceroom.calculator.util.JsonSession;

import java.util.List;

@Component
public class SessionFactory {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RaceRepository raceRepository;

    public List<Session> sessionBuilder(JsonRace jsonRace) {
        for (JsonSession jsonSession:jsonRace.getSessions()) {
            createSession(jsonSession, jsonRace);
        }
        return sessionRepository.getSessionsByRaceId(raceRepository.getRaceByServerAndTrackAndTrackLayout(jsonRace.getServer(),
                jsonRace.getTrack(),
                jsonRace.getTrackLayout()).getId());
    }

    private void createSession(JsonSession jsonSession, JsonRace jsonRace) {
        Session session = new Session();
        session.setType(jsonSession.getType());
        session.setRacename(createRacename(jsonRace));
        session.setRaceId(raceRepository.getRaceByServerAndTrackAndTrackLayout(jsonRace.getServer(),
                jsonRace.getTrack(),
                jsonRace.getTrackLayout()).getId());
        sessionRepository.save(session);
    }

    private String createRacename(JsonRace jsonRace) {
        return jsonRace.getServer() + "_" + jsonRace.getTrack() + "_" + jsonRace.getTrackLayout();
    }
}
