package com.interview.placeupdater.repository;

import com.interview.placeupdater.model.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceUpdaterRepository extends JpaRepository <PlaceEntity,Integer> {

    PlaceEntity findByUnlocodeAndNameAndIsActive(String unlocode, String name, String isActive);

    PlaceEntity findByNameAndIsActive(String name, String isActive);
}
