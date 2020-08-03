package com.interview.placeupdater.controller;

import com.interview.placeupdater.model.PlaceEntity;
import com.interview.placeupdater.model.request.PlaceUpdateRequest;
import com.interview.placeupdater.service.IPlaceUpdaterService;
import com.interview.placeupdater.utils.CsvUtils;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceUpdaterController {

    private final IPlaceUpdaterService placeUpdaterService;

    @PostMapping(path = "/update", consumes = "multipart/form-data")
    public void updateVendorPlace(HttpServletResponse response, @RequestParam("vendor-file") MultipartFile vendorFile) throws IOException {
        List<PlaceUpdateRequest> listRequests = CsvUtils.read(PlaceUpdateRequest.class, vendorFile.getInputStream());
        List<PlaceEntity> updatedPlaces = placeUpdaterService.updatePlaces(listRequests);

        String filename = "output-place.csv";
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        CsvUtils.write(response.getWriter(), updatedPlaces) ;
    }
}
