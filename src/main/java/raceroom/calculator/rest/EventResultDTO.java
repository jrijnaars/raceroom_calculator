package raceroom.calculator.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventResultDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("Player")
    private ReturnPlayerDTO player;

    @JsonProperty("Points")
    private int points;

    @JsonProperty("CarName")
    private String carname;
}
