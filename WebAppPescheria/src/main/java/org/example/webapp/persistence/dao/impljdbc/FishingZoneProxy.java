package org.example.webapp.persistence.dao.impljdbc;

import org.example.webapp.model.Fish;
import org.example.webapp.model.FishingZone;
import org.example.webapp.persistence.DBManager;

import java.util.List;

public class FishingZoneProxy extends FishingZone {

    public FishingZoneProxy(String name, String country) {
        super(name, country);
    }

    public List<Fish> getFishes() {
        if(this.fishes==null){
            this.fishes= DBManager.getInstance().getFishDao().findAllByFishingZone(this.name);
        }
        return fishes;
    }
}
