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
public class RistoranteDaoJDBC implements RistoranteDao {
    private final Connection connection;

    public RistoranteDaoJDBC() {
        this.connection = DBManager.getInstance().getConnection();
    }


    @Override
    public List<Ristorante> findAll() {
        List<Ristorante> ristoranti = new ArrayList<Ristorante>();
        String query = "select * from ristorante";

        System.out.println("going to execute:"+query);

        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Ristorante rist = new RistoranteProxy(rs.getString("nome"),rs.getString("descrizione"),rs.getString("ubicazione"));
                ristoranti.add(rist);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ristoranti;
    }


    @Override
    public RistoranteProxy findByPrimaryKey(String nome) {


        String query = "SELECT nome, descrizione, ubicazione FROM ristorante WHERE nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return  new RistoranteProxy(nome,resultSet.getString("descrizione"),resultSet.getString("ubicazione"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    @Override
    public void save(Ristorante ristorante) {
        String query = "INSERT INTO ristorante (nome, descrizione, ubicazione) VALUES (?, ?, ?) " +
                "ON CONFLICT (nome) DO UPDATE SET " +
                "   descrizione = EXCLUDED.descrizione , "+
                "   ubicazione = EXCLUDED.ubicazione ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ristorante.getNome());
            statement.setString(2, ristorante.getDescrizione());
            statement.setString(3, ristorante.getUbicazione());
            statement.executeUpdate();

            List<Piatto> piatti = ristorante.getPiatti();
            if(piatti==null || piatti.isEmpty()){
                return;
            }

            resetRelationsPresentInTheJoinTable(connection , ristorante.getNome());
            PiattoDao pd = DBManager.getInstance().getPiattoDao();

            for (Piatto tempP : piatti) {
                pd.save(tempP);
                insertJoinRistorantePiatto(connection , ristorante.getNome() , tempP.getNome());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetRelationsPresentInTheJoinTable(Connection connection, String nomeRistorante) throws Exception {

        String query="Delete FROM ristorante_piatto WHERE ristorante_nome= ? ";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomeRistorante);

        preparedStatement.execute();
    }

    private void insertJoinRistorantePiatto(Connection connection , String nomeRistorante, String nomePiatto) throws SQLException {

        String query="INSERT INTO ristorante_piatto (ristorante_nome,piatto_nome) VALUES (? , ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomeRistorante);
        preparedStatement.setString(2, nomePiatto);

        preparedStatement.execute();

    }


    @Override
    public void delete(Ristorante ristorante) {
        String query = "DELETE FROM ristorante WHERE nome = ?";
        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1, ristorante.getNome());
            st.executeUpdate();
            resetRelationsPresentInTheJoinTable(connection,ristorante.getNome());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ristorante> findAllByPiattoName(String piattoNome) {
        List<Ristorante> ristoranti = new ArrayList<>();
        String query = "SELECT r.nome, r.descrizione, r.ubicazione FROM ristorante r JOIN ristorante_piatto rp ON r.nome = rp.ristorante_nome WHERE rp.piatto_nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, piattoNome);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ristoranti.add(new Ristorante(rs.getString("nome"), rs.getString("descrizione"), rs.getString("ubicazione")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ristoranti;
    }

    public static void main(String[] args) {
        RistoranteDao ristoDao = DBManager.getInstance().getRistoranteDao();

        List<Ristorante> ristoranti = ristoDao.findAll();
        for (Ristorante ristorante : ristoranti) {
            System.out.println("Nome: " + ristorante.getNome());
            System.out.println("Descrizione: " + ristorante.getDescrizione());
            System.out.println("Ubicazione: " + ristorante.getUbicazione());
        }

        Ristorante ristCaricato = ristoDao.findByPrimaryKey("Ristorante Uno");
        List<Piatto> piattiCaricati = ristCaricato.getPiatti();
        for (Piatto piatto : piattiCaricati) {
            System.out.println("Piatto: " + piatto.getNome());
        }
    }
}


