package com.sda.propertyManager.controller;
import com.sda.propertyManager.model.Property;
import com.sda.propertyManager.service.PropertyService;
import com.sda.propertyManager.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping(value = "/findPropertyByPropertyId/{id}")
    public String findPropertyByPropertyId(@PathVariable(name = "id") Integer id, Model model) throws Exception {
        Property byId = propertyService.findPropertyByPropertyId(id);
        model.addAttribute("propertyById", byId);
        return "propertyView";
    }

    @GetMapping(value = "/all")
    public String findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                          @RequestParam(value = "size", defaultValue = "100") Integer size,
                          ModelMap modelMap) {
        List<Property> propertyList = propertyService.findAll(page, size);
        modelMap.addAttribute("propertyList", propertyList);
        return "propertyView";
    }
    @RequestMapping(path = "/createProperty", method = RequestMethod.GET)
    public String createPropertyView(ModelMap model) {
        model.addAttribute("property", new Property());
        return "createProperty";
    }

    @RequestMapping(path = "/createProperty", method = RequestMethod.POST)
    public String createProperty(@ModelAttribute Property property) {
        propertyService.createProperty(property);
        return "redirect:/property/all";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateView(ModelMap model, @RequestParam("propertyId") Integer propertyId) throws Exception {
        Property propertyToBeUpdated = propertyService.findPropertyByPropertyId(propertyId);
        model.addAttribute("property", propertyToBeUpdated);
        return "updateProperty";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ModelMap model, @ModelAttribute Property property) throws UserNotFoundException {
        propertyService.updateProperty(property.getPropertyId(), property);
        return "redirect:/property/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteProperty(ModelMap model, @RequestParam("propertyId") Integer propertyId) throws UserNotFoundException {
        propertyService.deleteProperty(propertyId);
        return "redirect:/property/all";
    }
}
