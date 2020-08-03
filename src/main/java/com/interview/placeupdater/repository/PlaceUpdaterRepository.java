package com.interview.placeupdater.repository;

import com.interview.placeupdater.model.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceUpdaterRepository extends JpaRepository <PlaceEntity,Integer> {

    PlaceEntity findByUnlocodeAndName(String unlocode, String name);

    PlaceEntity findByName(String name);
}
