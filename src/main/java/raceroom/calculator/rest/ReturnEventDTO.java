package raceroom.calculator.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnEventDTO {

    @JsonProperty("Id")
    private Long id;

    @JsonProperty("Server")
    private String server;

    @JsonProperty("StartTime")
    private int startTime;

    @JsonProperty("Time")
    private int time;
}
