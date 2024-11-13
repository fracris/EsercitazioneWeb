package org.example.webapp.persistence.dao.impljdbc;

import org.example.webapp.model.Fish;
import org.example.webapp.model.FishingZone;
import org.example.webapp.persistence.DBManager;

import java.util.List;

public class FishProxy extends Fish {

    public FishProxy(String name, String fisherman) {
        super(name, fisherman);
    }

    public List<FishingZone> getFishingZones() {
        if(this.fishingZones==null){
            this.fishingZones= DBManager.getInstance().getFishingZoneDao().findAllByFishName(this.name);
        }
        return fishingZones;
    }
}
