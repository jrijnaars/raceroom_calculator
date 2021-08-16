package raceroom.calculator.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SessionDTO {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Players")
    private List<PlayerDTO> player;

}
