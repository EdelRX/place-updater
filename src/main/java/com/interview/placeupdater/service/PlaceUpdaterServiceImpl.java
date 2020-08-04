package com.interview.placeupdater.service;

import com.interview.placeupdater.model.PlaceEntity;
import com.interview.placeupdater.model.request.PlaceUpdateRequest;
import com.interview.placeupdater.repository.PlaceUpdaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceUpdaterServiceImpl implements IPlaceUpdaterService {

    private final PlaceUpdaterRepository repository;

    @Override
    public List<PlaceEntity> updatePlaces(List<PlaceUpdateRequest> listRequests) {
        int primaryKeyOffset = (int) repository.count() + 1;

        for (PlaceUpdateRequest request : listRequests) {
            PlaceEntity place = null;
            if (request.getUNLOCODE() != null && !request.getUNLOCODE().isEmpty()) {
                place = repository.findByUnlocodeAndName(request.getUNLOCODE().replaceAll("\\s+", ""),
                        request.getPlaceName());
            } else {
                place = repository.findByName(request.getPlaceName());
            }
            if (place != null) {
                PlaceEntity newPlace = compareChanges(place, request);
                if (newPlace != null) {
                    place.setIs_active("f");
                    place.setUpdated_at(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ssZ")));
                    newPlace.setId(primaryKeyOffset);

                    repository.save(place);
                    repository.save(newPlace);

                    primaryKeyOffset++;
                }
            }
        }
        return repository.findAll();
    }


    public PlaceEntity compareChanges(PlaceEntity place, PlaceUpdateRequest request) {
        PlaceEntity newPlace = null;

        if (place.getVendor_place_id() == null || place.getVendor_place_id().isEmpty()) {
            newPlace = PlaceEntity.builder().vendor_place_id(request.getPlaceId())
                    .is_active("t").created_at(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ssZ")))
                    .updated_at(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ssZ"))).name(place.getName())
                    .unlocode(place.getUnlocode()).place_identity_id(place.getPlace_identity_id())
                    .build();
        }

        if (place.getName() == null || !place.getName().equals(request.getPlaceName())) {
            if (newPlace == null) {
                newPlace = PlaceEntity.builder().vendor_place_id(place.getVendor_place_id())
                        .is_active("t").created_at(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ssZ")))
                        .updated_at(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ssZ"))).unlocode(place.getUnlocode())
                        .place_identity_id(place.getPlace_identity_id())
                        .build();
            }
            newPlace.setName(request.getPlaceName());
        }

        if(!place.getUnlocode().equals(request.getUNLOCODE())){
            if (newPlace == null) {
                newPlace = PlaceEntity.builder().vendor_place_id(place.getVendor_place_id())
                        .is_active("t").created_at(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ssZ")))
                        .updated_at(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ssZ"))).name(place.getName())
                        .place_identity_id(place.getPlace_identity_id())
                        .build();
            }
            newPlace.setUnlocode(request.getUNLOCODE().replaceAll("\\s+", ""));
        }
        return newPlace;
    }
}
