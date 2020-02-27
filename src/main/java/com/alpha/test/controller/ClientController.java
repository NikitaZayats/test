package com.alpha.test.controller;

import com.alpha.test.entity.Client;
import com.alpha.test.exceptions.LevelRiskException;
import com.alpha.test.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
public class ClientController {


        private final ClientService service;

        @Autowired
        ClientController(ClientService service) {
            this.service = service;
        }

        @GetMapping()
        List<Client> all() {
            return service.all();
        }

        @PostMapping()
        ResponseEntity<Client> newClient(@RequestBody Client newClient) {
            return new ResponseEntity<>(service.newClient(newClient),HttpStatus.OK);
        }

        @PostMapping("/levelRisks")
        ResponseEntity<List<Client>> levelRisks(@RequestBody List<Long> ids){
            try {
                return new ResponseEntity<>(service.levelRisks(ids), HttpStatus.OK);
            }
            catch (LevelRiskException exc){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exc.getLocalizedMessage());
            }
        }

        @GetMapping("/{id}")
        ResponseEntity<Client> getById(@PathVariable long id) {
            Client client = service.getById(id);
            if(client == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(client, HttpStatus.OK);
        }

        @PutMapping()
        ResponseEntity<Client> updateClient(@RequestBody Client updateClient) {
            Client client = service.updateClient(updateClient);
            if(client == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(client, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        ResponseEntity deleteClient(@PathVariable long id) {
            if(service.deleteClient(id)){
                return new ResponseEntity(HttpStatus.OK);
            }else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }

}
