package raceroom.calculator.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncidentDTO {

    @JsonProperty("Type")
    private int type;

    @JsonProperty("Points")
    private int points;

    @JsonProperty("OtherUserId")
    private int otherUserId;
}
