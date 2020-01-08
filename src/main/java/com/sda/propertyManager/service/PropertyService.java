package com.sda.propertyManager.service;

import com.sda.propertyManager.model.Property;
import com.sda.propertyManager.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PropertyService {

    @Autowired
    PropertyRepository propertyRepository;


    public Property findPropertyByPropertyId(Integer propertyId) throws Exception {
        Optional<Property> property = propertyRepository.findById(propertyId);

        if (property.isPresent()) {
            return property.get();
        } else {
            throw new Exception("No client record exist for given id");
        }
    }

    public List<Property> findAll(Integer page, Integer size) {
        List<Property> propertyList = new ArrayList<>();
        List<Property> dbProperty = propertyRepository.findAll(PageRequest.of(page, size)).getContent();
        for (Property property : dbProperty) {
            propertyList.add(property);
        }
        return propertyList;
    }

    public Property createProperty(Property property) {
        if (property.getPropertyId() == null) {
            property = propertyRepository.save(property);
            return property;
        } else {
            Optional<Property> optionalProperty = propertyRepository.findById(property.getPropertyId());
            if (optionalProperty.isPresent()) {
                Property newProperty = optionalProperty.get();
                newProperty.setAddress(property.getAddress());
                newProperty.setDescription(property.getDescription());
                return newProperty;
            } else {
                property = propertyRepository.save(property);
                return property;
            }
        }
    }

    public List<Property> updateProperty(Integer id, Property property) throws UserNotFoundException {
        Optional<Property> optionalProperty = propertyRepository.findById(id);
        if (optionalProperty.isPresent()) {
            Property property1 = optionalProperty.get();
            property1.setAddress(property.getAddress());
            property1.setDescription(property.getDescription());
            propertyRepository.save(property1);
            List<Property> propertyList = new ArrayList<>();
            propertyRepository.findAll().forEach(c -> {
                propertyList.add(c);
            });
            return propertyList;
        } else {
            throw new UserNotFoundException(String.format("No user found with the id: %s!", id));
        }
    }

    public List<Property> deleteProperty(Integer propertyId) throws UserNotFoundException {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isPresent()) {
            propertyRepository.deleteById(propertyId);
            List<Property> propertyList = new ArrayList<>();
            propertyRepository.findAll().forEach(c -> {
                propertyList.add(c);
            });
            return propertyList;
        } else {
            throw new UserNotFoundException(String.format("No user found with the id: %s!, id"));
        }
    }

}


