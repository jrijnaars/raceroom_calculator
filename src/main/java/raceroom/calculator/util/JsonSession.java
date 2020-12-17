package raceroom.calculator.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JsonSession {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Players")
    private List<JsonPlayers> jsonPlayers;

}
