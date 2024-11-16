package com.dipartimento.demowebapplications.persistence.dao;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;

import java.util.List;

public interface PiattoDao {


    List<Piatto> findAll();

    Piatto findByPrimaryKey(String nome);

    void save(Piatto piatto);

    void delete(Piatto piatto);

    List<Piatto> findAllByRistoranteName(String name);




}
