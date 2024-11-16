package com.dipartimento.demowebapplications.service;


import com.dipartimento.demowebapplications.model.Ristorante;

import java.util.Collection;

public interface IRistoranteService {
    // list
    Collection<Ristorante> findAll();

    // retrieve byID
    Ristorante findById(String nome);

    // create
    Ristorante createRistorante(Ristorante ristorante) throws Exception;

    // update
    Ristorante updateRistorante(String nomeRistorante, Ristorante ristorante) throws Exception;

    // delete
    void deleteRistorante(String nome) throws Exception;
}
