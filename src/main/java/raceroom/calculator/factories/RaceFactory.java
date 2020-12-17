package raceroom.calculator.factories;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raceroom.calculator.model.Race;
import raceroom.calculator.repositories.RaceRepository;
import raceroom.calculator.util.JsonRace;

@Component
public class RaceFactory {

    @Autowired
    private RaceRepository raceRepository;

    public Race raceBuilder(JsonRace jsonRace) {
        racedataDuplicateCheck(jsonRace);
        Race race = createRace(jsonRace);
        raceRepository.save(race);
        return raceRepository.getRaceByServerAndTrackAndTrackLayout(
                jsonRace.getServer(),
                jsonRace.getTrack(),
                jsonRace.getTrackLayout());
    }

    @SneakyThrows
    private void racedataDuplicateCheck(JsonRace jsonRace) {
        Race existingRace = raceRepository.getRaceByServerAndTrackAndTrackLayout(
                jsonRace.getServer(),
                jsonRace.getTrack(),
                jsonRace.getTrackLayout());
        if (existingRace != null) {
            throw new Exception("Race data already uploaded");
        }
    }

    private Race createRace(JsonRace jsonRace) {
        Race race = new Race();
        race.setServer(jsonRace.getServer());
        race.setStartTime(jsonRace.getStartTime());
        race.setTime(jsonRace.getTime());
        race.setFuelUsage(jsonRace.getFuelUsage());
        race.setTireWear(jsonRace.getTireWear());
        race.setMechanicalDamage(jsonRace.getMechanicalDamage());
        race.setFlagRules(jsonRace.getFlagRules());
        race.setCutRules(jsonRace.getCutRules());
        race.setMandatoryPitstop(jsonRace.getMandatoryPitstop());
        race.setTrack(jsonRace.getTrack());
        race.setTrackLayout(jsonRace.getTrackLayout());
        return race;
    }
}