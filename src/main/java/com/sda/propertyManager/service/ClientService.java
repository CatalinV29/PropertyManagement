package com.sda.propertyManager.service;

import com.sda.propertyManager.model.Client;
import com.sda.propertyManager.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Client findClientByClientId(Integer clientId) throws Exception {
        Optional<Client> client = clientRepository.findById(clientId);

        if (client.isPresent()) {
            return client.get();
        } else {
            throw new Exception("No client record exist for given id");
        }
    }
//    public Client findClientByFirstName(String firstName) {
//        Client client = (Client) clientRepository.findClientByLastName(firstName);
//        return client;
//    }


    public Client findClientByLastName(String lastName) {
        Client client = (Client) clientRepository.findClientByLastName(lastName);
        return client;
    }

    public Client findClientByCnp(String cnp) {
        Client client = (Client) clientRepository.findClientByCnp(cnp);
        return client;
    }

    public List<Client> findAll(Integer page, Integer size) {
        List<Client> clients = new ArrayList<>();
        List<Client> dbClient = clientRepository.findAll(PageRequest.of(page, size)).getContent();
        for (Client client : dbClient) {
            clients.add(client);
        }
        return clients;
    }


    public List<Client> deleteClient(Integer clientId) throws UserNotFoundException {
        Optional<Client> clientById = clientRepository.findById(clientId);
        if (clientById.isPresent()) {
            clientRepository.deleteById(clientId);
            List<Client> clientList = new ArrayList<>();
            clientRepository.findAll().forEach(c -> {
                clientList.add(c);
            });
            return clientList;
        } else {
            throw new UserNotFoundException(String.format("No user found with the id: %s!, id"));
        }
    }

    public List<Client> updateClient(Integer id, Client client) throws UserNotFoundException {
        Optional<Client> clientById = clientRepository.findById(id);
        if (clientById.isPresent()) {
            Client client1 = clientById.get();
            client1.setFirstName(client.getFirstName());
            client1.setLastName(client.getLastName());
            client1.setEmail(client.getEmail());
            client1.setCnp(client.getCnp());
            client1.setPhoto(client.getPhoto());
            clientRepository.save(client1);
            List<Client> clientList = new ArrayList<>();
            clientRepository.findAll().forEach(c -> {
                clientList.add(c);
            });
            return clientList;
        } else {
            throw new UserNotFoundException(String.format("No user found with the id: %s!", id));
        }
    }


    public Client createClient(Client client) {
        if (client.getClientId() == null) {
            client = clientRepository.save(client);
            return client;
        } else {
            Optional<Client> client1 = clientRepository.findById(client.getClientId());
            if (client1.isPresent()) {
                Client newClient = client1.get();
                newClient.setFirstName(client.getFirstName());
                newClient.setLastName(client.getLastName());
                newClient.setEmail(client.getEmail());
                newClient.setCnp(client.getCnp());
                newClient.setPhoto(client.getPhoto());
                return newClient;
            } else {
                client = clientRepository.save(client);

                return client;
            }
        }
    }


  public List<Client> findClientByFirstName(String firstName){
        return clientRepository.findByFirstName(firstName);
  }
}