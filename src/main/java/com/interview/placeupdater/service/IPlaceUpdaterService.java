package com.interview.placeupdater.service;

import com.interview.placeupdater.model.PlaceEntity;
import com.interview.placeupdater.model.request.PlaceUpdateRequest;

import java.util.List;

public interface IPlaceUpdaterService {

    List<PlaceEntity>  updatePlaces(List<PlaceUpdateRequest> listRequests);
}
