package com.interview.placeupdater;

import com.interview.placeupdater.controller.PlaceUpdaterController;
import com.interview.placeupdater.model.PlaceEntity;
import com.interview.placeupdater.service.PlaceUpdaterServiceImpl;
import com.interview.placeupdater.utils.CsvUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PlaceUpdaterControllerTests {

    @Mock
    private PlaceUpdaterServiceImpl placeUpdaterService;

    private PlaceUpdaterController placeUpdaterController;

    private List<PlaceEntity> listUpdatedPlaces;

    @BeforeEach
    void initUseCase() throws IOException {
        placeUpdaterController = new PlaceUpdaterController(placeUpdaterService);
        listUpdatedPlaces = CsvUtils.read(PlaceEntity.class, (PlaceEntity.class).getResourceAsStream("/"+ "output-place-full.csv"));
    }

    @Test
    void updateVendorPlaceAndSuccess() throws IOException {
        Mockito.when(placeUpdaterService.updatePlaces(Mockito.any())).thenReturn(listUpdatedPlaces);

        ClassLoader classLoader = getClass().getClassLoader();

        MockMultipartFile file = new MockMultipartFile("vendor-place-full.csv",classLoader.getResourceAsStream("vendor-place-full.csv"));
        MockHttpServletResponse response = new MockHttpServletResponse();
        placeUpdaterController.updateVendorPlace(response,file);

        Assertions.assertTrue(!response.getContentAsString().isEmpty());
        Assertions.assertEquals(200,response.getStatus());
        Assertions.assertEquals("application/octet-stream", response.getContentType());
    }
}
