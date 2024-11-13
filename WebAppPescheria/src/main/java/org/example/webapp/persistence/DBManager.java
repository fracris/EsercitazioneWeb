package org.example.webapp.persistence;

import org.example.webapp.persistence.dao.FishDao;
import org.example.webapp.persistence.dao.FishingZoneDao;
import org.example.webapp.persistence.dao.impljdbc.FishDaoJDBC;
import org.example.webapp.persistence.dao.impljdbc.FishingZoneDaoJDBC;

import java.sql.*;

public class DBManager {
    private static DBManager instance = null;
    private Connection con = null;
    private FishingZoneDao fishingZoneDao = null;
    private FishDao fishDao = null;

    private DBManager() {}

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Pescheria", "postgres", "postgres");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return con;
    }

    public FishDao getFishDao() {
        if (fishDao == null) {
            fishDao = new FishDaoJDBC(getConnection());
        }
        return fishDao;
    }

    public FishingZoneDao getFishingZoneDao() {
        if (fishingZoneDao == null) {
            fishingZoneDao = new FishingZoneDaoJDBC(getConnection()) {
            };
        }
        return fishingZoneDao;
    }




    public static void main(String[] args) {
        Connection con = DBManager.getInstance().getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from fish");
            if (rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
