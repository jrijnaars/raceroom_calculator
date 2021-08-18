package raceroom.calculator.core;

import org.springframework.stereotype.Component;
import raceroom.calculator.repositories.PlayerResultEntity;

@Component
public class CalculatorFactory {

    protected int getDriverQualifyPoints(PlayerResultEntity player) {
        return player.getSession().getType().equals("qualify") ? player.getPoints() : 0;
    }
}
