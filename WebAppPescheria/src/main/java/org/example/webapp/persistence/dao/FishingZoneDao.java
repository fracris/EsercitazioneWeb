package org.example.webapp.persistence.dao;

import org.example.webapp.model.FishingZone;

import java.util.List;

public interface FishingZoneDao {
    List<FishingZone> findAll();
    FishingZone findByPrimaryKey(String name);
    void save(FishingZone fishingZone);
    void delete(FishingZone fishingZone);
    List<FishingZone> findAllByFishName(String name);
}
