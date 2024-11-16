package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.PiattoDao;
import com.dipartimento.demowebapplications.persistence.dao.RistoranteDao;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PiattoDaoJDBC implements PiattoDao {

    private final Connection connection;

    public PiattoDaoJDBC() {
        this.connection = DBManager.getInstance().getConnection();
    }

    @Override
    public List<Piatto> findAll() {
        List<Piatto> piatti = new ArrayList<>();
        String query = "SELECT * FROM piatto";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Piatto piatto = new PiattoProxy(rs.getString("nome"), rs.getString("ingredienti"));
                piatti.add(piatto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return piatti;
    }

    @Override
    public PiattoProxy findByPrimaryKey(String nome) {
        String query = "SELECT * FROM piatto WHERE nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new PiattoProxy(rs.getString("nome"), rs.getString("ingredienti"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void save(Piatto piatto) {
        String query = "INSERT INTO piatto(nome, ingredienti) VALUES(?, ?)"
                + "ON CONFLICT (nome) DO UPDATE SET ingredienti = EXCLUDED.ingredienti";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, piatto.getNome());
            st.setString(2, piatto.getIngredienti());
            st.executeUpdate();
            List<Ristorante> ristoranti = piatto.getRistoranti();
            if (ristoranti == null || ristoranti.isEmpty()) {
                return;
            }
            resetRelationsPresentInTheJoinTable(piatto.getNome());
            RistoranteDao rd = DBManager.getInstance().getRistoranteDao();
            for (Ristorante tempR : ristoranti) {
                rd.save(tempR);
                insertJoinRistorantePiatto(tempR.getNome(), piatto.getNome());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertJoinRistorantePiatto(String nomeRistorante, String nomePiatto) throws SQLException {
        String query = "INSERT INTO ristorante_piatto(ristorante_nome, piatto_nome) VALUES(?, ?)";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, nomeRistorante);
        st.setString(2, nomePiatto);
        st.execute();
    }

    private void resetRelationsPresentInTheJoinTable(String nome) throws SQLException {
        String query = "DELETE FROM ristorante_piatto WHERE piatto_nome = ?";
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1, nome);
        st.execute();
    }

    @Override
    public void delete(Piatto piatto) {
        String query = "DELETE FROM piatto WHERE nome = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, piatto.getNome());
            st.executeUpdate();
            resetRelationsPresentInTheJoinTable(piatto.getNome());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Piatto> findAllByRistoranteName(String ristoranteNome) {
        List<Piatto> piatti = new ArrayList<>();
        String query = "SELECT p.nome, p.ingredienti FROM piatto p " +
                "JOIN ristorante_piatto rp ON p.nome = rp.piatto_nome " +
                "WHERE rp.ristorante_nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ristoranteNome);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                piatti.add(new Piatto(rs.getString("nome"), rs.getString("ingredienti")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return piatti;
    }

    public static void main(String[] args) {
        PiattoDao piattoDao = DBManager.getInstance().getPiattoDao();
        List<Piatto> piatti = piattoDao.findAll();
        for (Piatto piatto : piatti) {
            System.out.println(piatto.getNome());
            System.out.println(piatto.getIngredienti());
        }
        Piatto piatto = piattoDao.findByPrimaryKey("Pizza");

        System.out.println("Piatto: " + piatto.getNome());
        System.out.println("Ingredienti: " + piatto.getIngredienti());

        System.out.println("Ristoranti che offrono il piatto:");
        for (Ristorante ristorante : piatto.getRistoranti()) {
            System.out.println("- " + ristorante.getNome() + ", ubicazione: " + ristorante.getUbicazione());
        }
    }
}
