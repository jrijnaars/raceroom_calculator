package raceroom.calculator.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JsonRace {

    @JsonProperty("Server")
    private String server;

    @JsonProperty("StartTime")
    private int startTime;

    @JsonProperty("Time")
    private int time;

    @JsonProperty("FuelUsage")
    private String fuelUsage;

    @JsonProperty("TireWear")
    private String tireWear;

    @JsonProperty("MechanicalDamage")
    private String mechanicalDamage;

    @JsonProperty("FlagRules")
    private String flagRules;

    @JsonProperty("CutRules")
    private String cutRules;

    @JsonProperty("MandatoryPitstop")
    private String mandatoryPitstop;

    @JsonProperty("Track")
    private String track;

    @JsonProperty("TrackLayout")
    private String trackLayout;

    @JsonProperty("Sessions")
    List<JsonSession> sessions;

}


