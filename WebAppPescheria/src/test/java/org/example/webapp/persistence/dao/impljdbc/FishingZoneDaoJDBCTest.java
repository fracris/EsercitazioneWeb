package org.example.webapp.persistence.dao.impljdbc;

import org.example.webapp.model.Fish;
import org.example.webapp.model.FishingZone;
import org.example.webapp.persistence.DBManager;
import org.example.webapp.persistence.dao.FishingZoneDao;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FishingZoneDaoJDBCTest {

    @Test
    void whenFindAll_thenRetrieveAll(){

        FishingZoneDao fishingZoneDao = DBManager.getInstance().getFishingZoneDao();

        List<FishingZone> all = fishingZoneDao.findAll();

        assertNotNull(all);

        assertEquals(1,all.size());

        for (FishingZone fishingZone : all) {
            System.out.println(fishingZone);
        }
    }

    @Test
    void whenTryToSaveANewFishingZone_Then_saveItCorrectly(){
        FishingZone fz= new FishingZone();
        fz.setName("FishingZone5");
        fz.setCountry("Contry5");

        fz.setFishes( Arrays.asList(
                new Fish("FISH10", "man10"),
                new Fish("FISH11", "man11")

        ));
        DBManager.getInstance().getFishingZoneDao().save(fz);
    }
}
