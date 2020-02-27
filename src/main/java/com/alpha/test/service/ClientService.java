package com.alpha.test.service;

import com.alpha.test.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> all();
    Client getById( long id);
    Client newClient(Client newClient);
    Client updateClient(Client updateClient);
    boolean deleteClient(long id);
    List<Client> levelRisks(List<Long> ids);




}
