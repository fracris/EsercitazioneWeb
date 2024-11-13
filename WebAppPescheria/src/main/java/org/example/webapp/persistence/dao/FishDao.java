package org.example.webapp.persistence.dao;

import org.example.webapp.model.Fish;

import java.util.List;

public interface FishDao {
    List<Fish> findAll();
    Fish findByPrimaryKey(String name);
    void save(Fish fish);
    void delete(Fish fish);
    List<Fish> findAllByFishingZone(String name);
}
