package raceroom.calculator.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecsDTO {

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("production_year")
    private String production_year;

    @JsonProperty("weight")
    private String weight;

    @JsonProperty("engine_type")
    private String engine_type;

    @JsonProperty("aspiration")
    private String aspiration;

    @JsonProperty("drive_type")
    private String drive_type;

    @JsonProperty("horse_power")
    private String horsePower;
}
