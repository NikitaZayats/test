package com.alpha.test.service;

import com.alpha.test.entity.Client;
import com.alpha.test.entity.ClientConstants;
import com.alpha.test.exceptions.LevelRiskException;
import com.alpha.test.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> all() {
        return clientRepository.findAll();
    }

    @Override
    public Client getById(long id) {
        Optional<Client> byId = clientRepository.findById(id);
        return byId.isPresent() ? byId.get() : null;
    }

    @Override
    public Client newClient(Client newClient) {
        return clientRepository.saveAndFlush(newClient);
    }

    @Override
    public Client updateClient(Client updateClient) {
        Optional<Client> byId = clientRepository.findById(updateClient.getId());
        if (byId.isPresent()) {
            Client client = byId.get();
            client.setRiskProfile(updateClient.getRiskProfile());
            return clientRepository.saveAndFlush(client);
        } else {
            return null;
        }

    }

    @Override
    public boolean deleteClient(long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Client> levelRisks(List<Long> ids) {
        List<Client> allById = clientRepository.findAllById(ids);
        if(ids.size() != allById.size()){
            throw new LevelRiskException("Invalid arguments");
        }
        String tempRisk = "";
        for (int i = 0; i < allById.size(); i++) {
            if (allById.get(i).getRiskProfile().equals(ClientConstants.HIGH_RISK)) {
                tempRisk = ClientConstants.HIGH_RISK;
                break;
            } else if (allById.get(i).getRiskProfile().equals(ClientConstants.NORMAL_RISK)) {
                tempRisk = ClientConstants.NORMAL_RISK;
            } else {
                tempRisk = ClientConstants.LOW_RISK;
            }
        }
        for (Client client : allById) {
            client.setRiskProfile(tempRisk);
            clientRepository.saveAndFlush(client);
        }

        return allById;
    }
}
