package com.sda.propertyManager.repository;
import com.sda.propertyManager.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    List<Property> findPropertyByAddress(String address);

    List<Property> findPropertyByPropertyId(Integer id);



}
