package com.dipartimento.demowebapplications.model;

import lombok.*;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class Piatto {

    protected String nome;
    protected String ingredienti;
    protected List<Ristorante> ristoranti;

    public Piatto(String nome, String ingredienti) {
        this.nome = nome;
        this.ingredienti = ingredienti;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIngredienti(String ingredienti) {
        this.ingredienti = ingredienti;
    }

    public void setRistoranti(List<Ristorante> ristoranti) {
        this.ristoranti = ristoranti;
    }
}
