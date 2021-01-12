package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.model.Race;
import raceroom.calculator.model.Session;
import raceroom.calculator.repositories.RaceRepository;
import raceroom.calculator.repositories.SessionRepository;
import raceroom.calculator.rest.RaceDTO;
import raceroom.calculator.rest.SessionDTO;

@Slf4j
@Component
public class SessionFactory {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private RaceRepository raceRepository;

    public void sessionBuilder(RaceDTO raceDTO) {
        for (SessionDTO sessionDTO : raceDTO.getSessions()) {
            createSession(sessionDTO, raceDTO);
        }
    }

    private void createSession(SessionDTO sessionDTO, RaceDTO raceDTO) {
        Race race = raceRepository.getRaceByServerAndTrackAndTrackLayout(raceDTO.getServer(),
                raceDTO.getTrack(),
                raceDTO.getTrackLayout());
        Session session = new Session();
        session.setType(sessionDTO.getType());
        session.setRacename(race.getRaceName());
        session.setRaceId(race.getId());
        sessionRepository.save(session);
        log.info("Session {} is saved in the database", session.getType());
    }


    public void sessionCalculator(RaceDTO raceDTO) {

    }
}
