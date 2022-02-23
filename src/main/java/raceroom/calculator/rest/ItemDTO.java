package raceroom.calculator.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO {

    @JsonProperty("name")
    private String carName;

    @JsonProperty("car_class")
    private CarclassDTO carclassDTO;

    @JsonProperty("specs_data")
    private SpecsDTO specsDTO;
}
