package raceroom.calculator.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeasonResultDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("Player")
    private PlayerDTO player;

    @JsonProperty("Points")
    private int points;
}
