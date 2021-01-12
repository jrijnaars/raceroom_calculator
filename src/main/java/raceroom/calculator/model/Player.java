package raceroom.calculator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {

    private long raceId;

    private String sessionType;

    private int userId;

    private String fullName;

    private String username;

    private int userWeightPenalty;

    private int carId;

    private String car;

    private int carWeightPenalty;

    private int position;

    private int positionInClass;

    private int startPosition;

    private int startPositionInClass;

    private String finishStatus;
}
