package raceroom.calculator.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SeasonSummaryDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("Events")
    private List<ReturnEventDTO> events;

    @JsonProperty("Results")
    private List<SeasonResultDTO> results;
}
