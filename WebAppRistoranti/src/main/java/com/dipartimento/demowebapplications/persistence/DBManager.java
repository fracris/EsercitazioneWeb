package com.dipartimento.demowebapplications.persistence;

import com.dipartimento.demowebapplications.persistence.dao.PiattoDao;
import com.dipartimento.demowebapplications.persistence.dao.RistoranteDao;
import com.dipartimento.demowebapplications.persistence.dao.impljdbc.PiattoDaoJDBC;
import com.dipartimento.demowebapplications.persistence.dao.impljdbc.RistoranteDaoJDBC;

import java.sql.*;

public class DBManager {
    private static DBManager instance = null;
    private Connection con = null;
    private RistoranteDao ristoranteDao = null;
    private PiattoDao piattoDao = null;

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
                con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ristoranti", "postgres", "postgres");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return con;
    }

    public PiattoDao getPiattoDao() {
        if (piattoDao == null) {
            piattoDao = new PiattoDaoJDBC(getConnection());
        }
        return piattoDao;
    }

    public RistoranteDao getRistoranteDao() {
        if (ristoranteDao == null) {
            ristoranteDao = new RistoranteDaoJDBC(getConnection());
        }
        return ristoranteDao;
    }




    public static void main(String[] args) {
        Connection con = DBManager.getInstance().getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from utenti");
            if (rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
