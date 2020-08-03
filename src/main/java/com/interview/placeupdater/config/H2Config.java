package com.interview.placeupdater.config;

import com.interview.placeupdater.model.PlaceEntity;
import com.interview.placeupdater.repository.PlaceUpdaterRepository;
import com.interview.placeupdater.utils.CsvUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class H2Config {

    private final PlaceUpdaterRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadDatabase() throws IOException {
        repository.saveAll(CsvUtils.read(PlaceEntity.class,new ClassPathResource("company-place.csv").getInputStream()));
    }
}
