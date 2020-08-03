package com.interview.placeupdater;

import com.interview.placeupdater.model.PlaceEntity;
import com.interview.placeupdater.model.request.PlaceUpdateRequest;
import com.interview.placeupdater.repository.PlaceUpdaterRepository;
import com.interview.placeupdater.service.PlaceUpdaterServiceImpl;
import com.interview.placeupdater.utils.CsvUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PlaceUpdaterServiceTests {

    @Mock
    private PlaceUpdaterRepository repository;

    private PlaceUpdaterServiceImpl placeUpdaterService;

    private List<PlaceUpdateRequest> listRequestsPlaceID;

    private List<PlaceEntity> listPlacesID;

    private List<PlaceEntity> listUpdatedPlacesID;

    private List<PlaceUpdateRequest> listRequestsUnlocode;

    private List<PlaceEntity> listPlacesUnlocode;

    private List<PlaceEntity> listUpdatedUnlocode;

    private List<PlaceUpdateRequest> listRequestsPlaceName;

    private List<PlaceEntity> listPlacesName;

    private List<PlaceEntity> listUpdatedPlaceName;


    @BeforeEach
    void initUseCase() throws IOException {
        placeUpdaterService = new PlaceUpdaterServiceImpl(repository);

        listRequestsPlaceID = CsvUtils.read(PlaceUpdateRequest.class, (PlaceUpdateRequest.class).getResourceAsStream("/"+ "vendor-place-placeID.csv"));
        listPlacesID = CsvUtils.read(PlaceEntity.class, (PlaceEntity.class).getResourceAsStream("/"+"company-place-placeID.csv"));
        listUpdatedPlacesID = CsvUtils.read(PlaceEntity.class, (PlaceEntity.class).getResourceAsStream("/"+"output-place-placeID.csv"));

        listRequestsUnlocode = CsvUtils.read(PlaceUpdateRequest.class, (PlaceUpdateRequest.class).getResourceAsStream("/"+ "vendor-place-unlocode.csv"));
        listPlacesUnlocode = CsvUtils.read(PlaceEntity.class, (PlaceEntity.class).getResourceAsStream("/"+"company-place-unlocode.csv"));
        listUpdatedUnlocode = CsvUtils.read(PlaceEntity.class, (PlaceEntity.class).getResourceAsStream("/"+"output-place-unlocode.csv"));

        listRequestsPlaceName = CsvUtils.read(PlaceUpdateRequest.class, (PlaceUpdateRequest.class).getResourceAsStream("/"+ "vendor-place-name.csv"));
        listPlacesName = CsvUtils.read(PlaceEntity.class, (PlaceEntity.class).getResourceAsStream("/"+"company-place-name.csv"));
        listUpdatedPlaceName = CsvUtils.read(PlaceEntity.class, (PlaceEntity.class).getResourceAsStream("/"+"output-place-name.csv"));
    }

    @Test
    void updatePlaceIdAndSuccess(){
        Mockito.when(repository.count()).thenReturn((long) listPlacesID.size());
        Mockito.when(repository.findByUnlocodeAndName(Mockito.anyString(), Mockito.anyString())).thenReturn(listPlacesID.get(0));
        Mockito.when(repository.findAll()).thenReturn(listUpdatedPlacesID);

        List<PlaceEntity> listResults = placeUpdaterService.updatePlaces(listRequestsPlaceID);

        Assertions.assertEquals("",listResults.get(0).getVendor_place_id());
        Assertions.assertEquals("f",listResults.get(0).getIs_active());
        Assertions.assertEquals(listResults.get(1).getVendor_place_id(),listRequestsPlaceID.get(0).getPlaceId());
        Assertions.assertEquals("t",listResults.get(1).getIs_active());
    }

    @Test
    void updateUnlocodeAndPlaceIdAndSuccess(){
        Mockito.when(repository.count()).thenReturn((long) listPlacesUnlocode.size());
        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(listPlacesUnlocode.get(0));
        Mockito.when(repository.findAll()).thenReturn(listUpdatedUnlocode);

        List<PlaceEntity> listResults = placeUpdaterService.updatePlaces(listRequestsUnlocode);

        Assertions.assertEquals("",listResults.get(0).getVendor_place_id());
        Assertions.assertEquals("f",listResults.get(0).getIs_active());
        Assertions.assertEquals(listResults.get(1).getVendor_place_id(),listRequestsUnlocode.get(0).getPlaceId());
        Assertions.assertEquals("t",listResults.get(1).getIs_active());

        Assertions.assertEquals("",listResults.get(1).getUnlocode());
        Assertions.assertEquals(listResults.get(0).getUnlocode(), listPlacesUnlocode.get(0).getUnlocode());

    }

    @Test
    void updatePlaceNameAndSuccess(){
        Mockito.when(repository.count()).thenReturn((long) listPlacesName.size());
        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(listPlacesName.get(0));
        Mockito.when(repository.findAll()).thenReturn(listUpdatedPlaceName);

        List<PlaceEntity> listResults = placeUpdaterService.updatePlaces(listRequestsPlaceName);

        Assertions.assertEquals(listResults.get(1).getName(),listRequestsPlaceName.get(0).getPlaceName());
        Assertions.assertEquals("t", listResults.get(1).getIs_active());
        Assertions.assertEquals("f", listResults.get(0).getIs_active());
    }

}
