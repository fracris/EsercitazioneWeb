package com.dipartimento.demowebapplications.controller;

import com.dipartimento.demowebapplications.exception.RistoranteNotValidException;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.service.IRistoranteService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;

@RestController
@RequestMapping("/api/ristorante/v1")
class RistoranteController {
    private final IRistoranteService ristoranteService;

    public RistoranteController(IRistoranteService ristoranteService) {
        this.ristoranteService = ristoranteService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<Collection<Ristorante>> getAllRistoranti(){
        Collection<Ristorante> all = this.ristoranteService.findAll();
        return ResponseEntity.ok(all);
    }

    @RequestMapping(value = "/{nomeRistorante}", method = RequestMethod.GET)
        // opzione 1
//    ResponseEntity<Ristorante> getRistoranteById(@PathVariable("nomeRistorante") String nome){
        // opzione 2
    ResponseEntity<Ristorante> getRistoranteById(@PathVariable String nomeRistorante){
        return ResponseEntity.ok(
                this.ristoranteService.findById(nomeRistorante)
        );
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<Ristorante> postCreateNewRistorante(@RequestBody  Ristorante ristorante) throws Exception {
        try{
            return ResponseEntity.ok(
                    this.ristoranteService.createRistorante(ristorante)
            );
        }catch (RistoranteNotValidException e){
            return new ResponseEntity(e.getMessage() , HttpStatusCode.valueOf(400));
            // return ResponseEntity.status(HttpStatusCode.valueOf(400) , ).build();
        }
    }

    @RequestMapping(value = "/{nomeRistorante}", method = RequestMethod.POST)
    ResponseEntity<Ristorante> postUpdateRistorante(
            @PathVariable String nomeRistorante,
            @RequestBody Ristorante ristorante
    ) throws Exception {
        return ResponseEntity.ok(
                this.ristoranteService.updateRistorante(nomeRistorante, ristorante)
        );
    }

    @RequestMapping(value = "/{nomeRistorante}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteRistorante(String nome) throws Exception {
        this.ristoranteService.deleteRistorante(nome);
        return ResponseEntity.ok().build();
    }
}