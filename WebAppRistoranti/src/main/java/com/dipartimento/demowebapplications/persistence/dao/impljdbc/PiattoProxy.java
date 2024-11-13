package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;
import java.util.List;

public class PiattoProxy extends Piatto {
    private List<Ristorante> ristoranti = null;

    public PiattoProxy(String nome, String ingredienti) {
        super(nome, ingredienti);
    }

    public List<Ristorante> getRistoranti() {
        if (ristoranti == null) {
            ristoranti = DBManager.getInstance().getRistoranteDao().findAllByPiattoName(this.getNome());
        }
        return ristoranti;
    }

}
