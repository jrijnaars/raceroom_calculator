package raceroom.calculator.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnPlayerDTO {

    @JsonProperty("userId")
    private Long id;

    @JsonProperty("FullName")
    private String fullName;

    @JsonProperty("UserName")
    private String userName;



}
