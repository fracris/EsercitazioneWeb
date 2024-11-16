package com.dipartimento.demowebapplications.service;

import com.dipartimento.demowebapplications.exception.RistoranteNotValidException;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.dao.RistoranteDao;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
class RistoranteService implements IRistoranteService {

    //opzione autowiring 1
//    @Autowired
//    private  RistoranteDao dao;

    // opzione autowiring 2
    private final RistoranteDao ristoranteDao;

    RistoranteService(RistoranteDao ristoranteDao) {
        this.ristoranteDao = ristoranteDao;
    }


    @Override
    public Collection<Ristorante> findAll() {
        return ristoranteDao.findAll();
    }

    @Override
    public Ristorante findById(String nome) {
        return ristoranteDao.findByPrimaryKey(nome);
    }

    @Override
    public Ristorante createRistorante(Ristorante ristorante) throws Exception {

        System.out.println("Doing create piatto");
        checkRistoranteIsValid(ristorante);

        //verify that not exists a Ristorante with the same name...
        Ristorante byPrimaryKey = ristoranteDao.findByPrimaryKey(ristorante.getNome());
        if(byPrimaryKey!=null){
            throw new Exception("Already exists a Ristorante whit the same name:"+ristorante.getNome());
        }
        ristoranteDao.save(ristorante);
        return ristoranteDao.findByPrimaryKey(ristorante.getNome());
    }

    private void checkRistoranteIsValid(Ristorante ristorante) throws RistoranteNotValidException {
        if(ristorante==null){
            throw new RistoranteNotValidException("Ristorante must be not null");
        }
        if(ristorante.getNome()==null || ristorante.getNome().isEmpty()){
            throw new RistoranteNotValidException("Ristorante.nome must be not null and not empty");
        }
        if (ristorante.getDescrizione() == null || ristorante.getDescrizione().isEmpty()) {
            throw new RistoranteNotValidException("Ristorante.descrizione must be not null and not empty");
        }
        if (ristorante.getUbicazione() == null || ristorante.getUbicazione().isEmpty()) {
            throw new RistoranteNotValidException("Ristorante.ubicazione must be not null and not empty");
        }
    }

    @Override
    public Ristorante updateRistorante(String nomeRistorante, Ristorante ristorante) throws Exception {

        System.out.println("Doing update");
        checkRistoranteIsValid(ristorante);

        Ristorante byPrimaryKey = ristoranteDao.findByPrimaryKey(nomeRistorante);
        if(byPrimaryKey==null){
            throw new Exception("Not exists a Ristorante with the name:"+ristorante.getNome());
        }

        ristorante.setNome(nomeRistorante);


        ristoranteDao.save(ristorante);

        return ristoranteDao.findByPrimaryKey(ristorante.getNome());
    }

    @Override
    public void deleteRistorante(String nome) throws Exception {
        Ristorante byPrimaryKey = ristoranteDao.findByPrimaryKey(nome);
        if(byPrimaryKey==null){
            throw new Exception("Not exists a Ristorante with the name:"+nome);
        }
        this.ristoranteDao.delete(byPrimaryKey);
    }
}
