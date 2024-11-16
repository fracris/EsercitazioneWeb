package com.dipartimento.demowebapplications.persistence.dao;

import com.dipartimento.demowebapplications.model.Ristorante;

import java.util.List;

public interface RistoranteDao {
    List<Ristorante> findAll();
    Ristorante findByPrimaryKey(String nome);
    void save(Ristorante ristorante);
    void delete(Ristorante ristorante);
    List<Ristorante> findAllByPiattoName(String piattoNome);
}
