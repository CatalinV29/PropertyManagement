package com.sda.propertyManager.repository;

import com.sda.propertyManager.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByFirstName(String firstName);

    List<Client> findClientByClientId(Integer clientId);

    List<Client> findClientByFirstName(String firstName);

    List<Client> findClientByLastName(String lastName);

    List<Client> findClientByCnp(String cnp);


}
