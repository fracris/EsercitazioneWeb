package org.example.webapp.persistence.dao.impljdbc;

import org.example.webapp.model.Fish;
import org.example.webapp.model.FishingZone;
import org.example.webapp.persistence.DBManager;
import org.example.webapp.persistence.dao.FishDao;
import org.example.webapp.persistence.dao.FishingZoneDao;

import java.sql.*;
import java.util.*;

public class FishingZoneDaoJDBC implements FishingZoneDao {
    Connection connection = null;

    public FishingZoneDaoJDBC(Connection conn){
        this.connection = conn;
    }

    @Override
    public List<FishingZone> findAll() {
        List<FishingZone> fishingZones = new ArrayList<>();
        String query = "select * from fishingzone";

        System.out.println("going to execute:"+query);

        Statement st;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                FishingZone fishingZone = new FishingZoneProxy(rs.getString("name"),rs.getString("country"));
                fishingZones.add(fishingZone);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fishingZones;
    }


    @Override
    public FishingZone findByPrimaryKey(String name) {
        String query = "SELECT name, country FROM fishingzone WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new FishingZoneProxy(name,resultSet.getString("country"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    @Override
    public void save(FishingZone fishingZone) {
        String query = "INSERT INTO fishingzone (name, country) VALUES (?, ?) " +
                "ON CONFLICT (name) DO UPDATE SET " +
                "   country = EXCLUDED.country ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fishingZone.getName());
            statement.setString(2, fishingZone.getCountry());
            statement.executeUpdate();

            List<Fish> fishes = fishingZone.getFishes();
            if(fishes==null || fishes.isEmpty()){
                return;
            }

            resetRelationsPresentInTheJoinTable(connection , fishingZone.getName());
            FishDao pd = DBManager.getInstance().getFishDao();

            for (Fish tempF : fishes) {
                pd.save(tempF);
                insertJoinZoneFish(connection , fishingZone.getName() , tempF.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetRelationsPresentInTheJoinTable(Connection connection, String fishingzone) throws Exception {

        String query="Delete FROM fishingzone_fish WHERE fishingzone= ? ";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, fishingzone);


        preparedStatement.execute();

    }

    private void insertJoinZoneFish(Connection connection , String fishingzone, String fish_name) throws SQLException {

        String query="INSERT INTO fishingzone_fish (fishingzone,fish_name) VALUES (? , ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, fishingzone);
        preparedStatement.setString(2, fish_name);

        preparedStatement.execute();

    }


    @Override
    public void delete(FishingZone fishingZone) {
        String query = "DELETE FROM fishingZone WHERE name = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, fishingZone.getName());
            st.executeUpdate();
            resetRelationsPresentInTheJoinTable(connection,fishingZone.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FishingZone> findAllByFishName(String fish_name) {
        List<FishingZone> fishingZones = new ArrayList<>();
        String query = "SELECT fz.name, fz.country FROM fishingzone fz JOIN fishingzone_fish zf ON fz.name = zf.fishingzone WHERE zf.fish_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fish_name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                fishingZones.add(new FishingZoneProxy(rs.getString("nome"),rs.getString("country")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fishingZones;
    }

    public static void main(String[] args) {
        FishingZoneDao fishingZoneDao = DBManager.getInstance().getFishingZoneDao();

        List<FishingZone> fishingZones = fishingZoneDao.findAll();
        for (FishingZone fishingZone : fishingZones) {
            System.out.println("Name: " + fishingZone.getName());
            System.out.println("Country: " + fishingZone.getCountry());
        }

        FishingZone uploadedFishingZone = fishingZoneDao.findByPrimaryKey("FishingZone 1");
        List<Fish> uploadedFishes = uploadedFishingZone.getFishes();
        for (Fish fish : uploadedFishes) {
            System.out.println("Fish: " + fish.getName());
        }
    }
}
