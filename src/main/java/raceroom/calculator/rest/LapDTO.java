package raceroom.calculator.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LapDTO {

    @JsonProperty("Time")
    private int time;

    @JsonProperty("PitStopOccured")
    private boolean pitStopOccured;

    @JsonProperty("Incidents")
    private List<IncidentDTO> incident;

}


