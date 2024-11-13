package org.example.webapp.persistence.dao.impljdbc;

import org.example.webapp.model.Fish;
import org.example.webapp.model.FishingZone;
import org.example.webapp.persistence.DBManager;
import org.example.webapp.persistence.dao.FishDao;
import org.example.webapp.persistence.dao.FishingZoneDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FishDaoJDBC implements FishDao {
    Connection connection;

    public FishDaoJDBC(Connection conn){
        this.connection = conn;
    }

    @Override
    public List<Fish> findAll() {
        List<Fish> fishes = new ArrayList<>();
        String query = "select * from fish";
        Statement st;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Fish fish = new FishProxy(rs.getString("name"),rs.getString("fisherman"));
                fishes.add(fish);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fishes;
    }

    @Override
    public Fish findByPrimaryKey(String name) {
        String query = "SELECT name, fisherman FROM fish WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new FishProxy(resultSet.getString("name"), resultSet.getString("fisherman"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void save(Fish fish) {

        /*
        INSERT INTO t VALUES (1,'foo updated'),(3,'new record')
ON CONFLICT (id) DO UPDATE SET txt = EXCLUDED.txt;
         */


        String query = "INSERT INTO fish (name, fisherman) VALUES (?, ?) " +
                "ON CONFLICT (name) DO UPDATE SET fisherman = EXCLUDED.fisherman";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fish.getName());
            statement.setString(2, fish.getFisherman());
            statement.executeUpdate();
            List<FishingZone> fishingZones = fish.getFishingZones();
            if (fishingZones == null || fishingZones.isEmpty()) {
                return;
            }
            resetRelationsPresentInTheJoinTable(fish.getName());
            FishingZoneDao fz = DBManager.getInstance().getFishingZoneDao();
            for (FishingZone tempZ : fishingZones) {
                fz.save(tempZ);
                insertJoinZoneFish(tempZ.getName(), fish.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertJoinZoneFish(String fishingzone, String fish_name) throws SQLException {
        String query = "INSERT INTO fishingzone_fish(fishingzone, fish_name) VALUES(?, ?)";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, fishingzone);
        st.setString(2, fish_name);
        st.execute();
    }

    private void resetRelationsPresentInTheJoinTable(String name) throws SQLException {
        String query = "DELETE FROM fishingzone_fish WHERE fish_name = ?";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, name);
        st.execute();
    }

    @Override
    public void delete(Fish fish) {
        String query = "DELETE FROM fish WHERE name = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, fish.getName());
            st.executeUpdate();
            resetRelationsPresentInTheJoinTable(fish.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Fish> findAllByFishingZone(String fishingzone) {

        List<Fish> fishes = new ArrayList<>();
        String query = "SELECT f.name, f.fisherman FROM fish f " +
                "JOIN fishingzone_fish zf ON f.name = zf.fish_name " +
                "WHERE zf.fishingzone = ?";

        System.out.println("going to execute:"+query);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fishingzone);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                fishes.add(new Fish(resultSet.getString("name"), resultSet.getString("fisherman")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fishes;
    }

    public static void main(String[] args) {
        FishDao fishDao = DBManager.getInstance().getFishDao();
        List<Fish> fishes = fishDao.findAll();
        for (Fish fish : fishes) {
            System.out.println(fish.getName());
            System.out.println(fish.getFisherman());
        }
    }
}
