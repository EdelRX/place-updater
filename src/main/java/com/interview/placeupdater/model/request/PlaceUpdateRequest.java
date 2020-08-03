package com.interview.placeupdater.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceUpdateRequest {

    @JsonProperty("PLACE ID")
    private String placeId;
    @JsonProperty("PLACE NAME")
    private String placeName;
    @JsonProperty("LATITUDE")
    private Double latitude;
    @JsonProperty("UNLOCODE")
    private String UNLOCODE;
}
