package raceroom.calculator.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.PlayerResultEntity;
import raceroom.calculator.rest.IncidentDTO;
import raceroom.calculator.rest.LapDTO;
import raceroom.calculator.rest.PlayerDTO;

@Component
@Slf4j
public class RaceService extends CalculatorFactory {

    public void setQualifyPoints(PlayerResultEntity playerResultEntity) {
        if (playerResultEntity.getSession().getType().equals("Qualify") && playerResultEntity.getPosition() == 1) {
            playerResultEntity.setPoints(6);
        }
    }

    public void setRacePoints(PlayerResultEntity playerResultEntity) {
        if (playerResultEntity.getSession().getType().contains("Race")){
            setPointsByRacePosition(playerResultEntity);
            excludeDidNotFinish(playerResultEntity);
            excludeDisqualified(playerResultEntity);
        }
    }

    private void excludeDisqualified(PlayerResultEntity playerResultEntity) {
        if (playerResultEntity.getFinishStatus().equals("Disqualified")) {
            playerResultEntity.setPoints(0);
        }
    }

    private void excludeDidNotFinish(PlayerResultEntity playerResultEntity) {
        if (playerResultEntity.getFinishStatus().equals("DidNotFinish")) {
            playerResultEntity.setPoints(0);
        }
    }

    private void setPointsByRacePosition(PlayerResultEntity playerResultEntity) {
        switch (playerResultEntity.getPosition()) {
            case 1:
                playerResultEntity.setPoints(50);
                break;
            case 2:
                playerResultEntity.setPoints(44);
                break;
            case 3:
                playerResultEntity.setPoints(40);
                break;
            case 4:
                playerResultEntity.setPoints(37);
                break;
            case 5:
                playerResultEntity.setPoints(34);
                break;
            case 6:
                playerResultEntity.setPoints(32);
                break;
            case 7:
                playerResultEntity.setPoints(30);
                break;
            case 8:
                playerResultEntity.setPoints(28);
                break;
            case 9:
                playerResultEntity.setPoints(26);
                break;
            case 10:
                playerResultEntity.setPoints(25);
                break;
            case 11:
                playerResultEntity.setPoints(24);
                break;
            case 12:
                playerResultEntity.setPoints(23);
                break;
            case 13:
                playerResultEntity.setPoints(22);
                break;
            case 14:
                playerResultEntity.setPoints(21);
                break;
            case 15:
                playerResultEntity.setPoints(20);
                break;
            case 16:
                playerResultEntity.setPoints(19);
                break;
            case 17:
                playerResultEntity.setPoints(18);
                break;
            case 18:
                playerResultEntity.setPoints(17);
                break;
            case 19:
                playerResultEntity.setPoints(16);
                break;
            default:
                playerResultEntity.setPoints(15);
                break;
        }

    }

    public int calculateIncidentPoints(PlayerDTO playerDTO, String sessionType) {
        int totalIncidentsPoint = 0;

        if (sessionType.contains("Race")) {
            for (LapDTO lapDTO : playerDTO.getRaceSessionLaps()) {
                for (IncidentDTO incidentDTO : lapDTO.getIncident()) {
                    totalIncidentsPoint = totalIncidentsPoint + incidentDTO.getPoints();
                }
            }
        }
        return totalIncidentsPoint;
    }
}
