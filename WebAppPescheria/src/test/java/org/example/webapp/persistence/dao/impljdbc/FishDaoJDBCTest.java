package org.example.webapp.persistence.dao.impljdbc;

import org.example.webapp.model.Fish;
import org.example.webapp.persistence.DBManager;
import org.example.webapp.persistence.dao.FishDao;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FishDaoJDBCTest {
    @Test
    void whenCallFindAll_ReturnAllValues(){

        FishDao fishDao = DBManager.getInstance().getFishDao();
        List<Fish> all = fishDao.findAll();

        assertNotNull(all);

        assertEquals(3, all.size());

        for (Fish fish : all) {
            System.out.println(fish);
        }

    }
}
