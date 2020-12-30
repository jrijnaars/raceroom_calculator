package raceroom.calculator.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.model.Race;
import raceroom.calculator.repositories.RaceRepository;
import raceroom.calculator.rest.RaceDTO;

@Slf4j
@Component
public class RaceFactory {

    @Autowired
    private RaceRepository raceRepository;

    public void raceBuilder(RaceDTO raceDTO) {
        racedataDuplicateCheck(raceDTO);
        Race race = createRace(raceDTO);
        raceRepository.save(race);
        log.info("Race {} is uploaded in the database", race.getRaceName());
    }

    @SneakyThrows
    private void racedataDuplicateCheck(RaceDTO raceDTO) {
        Race existingRace = raceRepository.getRaceByServerAndTrackAndTrackLayout(
                raceDTO.getServer(),
                raceDTO.getTrack(),
                raceDTO.getTrackLayout());
        if (existingRace != null) {
            throw new Exception("Race data already uploaded");
        }
    }

    private Race createRace(RaceDTO raceDTO) {
        Race race = new Race();
        race.setServer(raceDTO.getServer());
        race.setStartTime(raceDTO.getStartTime());
        race.setTime(raceDTO.getTime());
        race.setFuelUsage(raceDTO.getFuelUsage());
        race.setTireWear(raceDTO.getTireWear());
        race.setMechanicalDamage(raceDTO.getMechanicalDamage());
        race.setFlagRules(raceDTO.getFlagRules());
        race.setCutRules(raceDTO.getCutRules());
        race.setMandatoryPitstop(raceDTO.getMandatoryPitstop());
        race.setTrack(raceDTO.getTrack());
        race.setTrackLayout(raceDTO.getTrackLayout());
        race.setRaceName(createRacename(raceDTO));
        return race;
    }

    private String createRacename(RaceDTO raceDTO) {
        return raceDTO.getServer() + "_" + raceDTO.getTrack() + "_" + raceDTO.getTrackLayout();
    }
}
