package raceroom.calculator.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {

    @JsonProperty("UserId")
    private int userId;

    @JsonProperty("FullName")
    private String fullName;

    @JsonProperty("Username")
    private String username;

    @JsonProperty("UserWeightPenalty")
    private int userWeightPenalty;

    @JsonProperty("CarId")
    private int carId;

    @JsonProperty("Car")
    private String car;

    @JsonProperty("CarWeightPenalty")
    private int carWeightPenalty;

    @JsonProperty("Position")
    private int position;

    @JsonProperty("PositionInClass")
    private int positionInClass;

    @JsonProperty("StartPosition")
    private int startPosition;

    @JsonProperty("StartPositionInClass")
    private int startPositionInClass;

    @JsonProperty("FinishStatus")
    private String finishStatus;
}
