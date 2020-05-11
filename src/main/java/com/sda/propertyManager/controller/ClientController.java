package com.sda.propertyManager.controller;

import com.sda.propertyManager.model.Client;
import com.sda.propertyManager.service.ClientService;
import com.sda.propertyManager.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/findById")
    public String listClients(Model model){
        model.addAttribute("clients", clientService.findAll(0,100));
        return "clientView";
    }



    @GetMapping(value = "/findClientByClientId/{id}")
    public String findClientByClientId(@PathVariable(name = "id") Integer id, Model model) throws Exception {
        Client byId = clientService.findClientByClientId(id);
        model.addAttribute("clientById", byId);
        return "clientView";
    }



    @GetMapping(value = "/findClientByFirstName")
    public String listClients(@RequestParam(defaultValue = "") String firstName, Model model ) {
        model.addAttribute("clientList", clientService.findClientByFirstName(firstName));
        return "clientView";
    }


    @GetMapping(value = "/findClientByLastName/{lastName}")
    public String findClientByLastName(@PathVariable(name = "lastName") String lastName, Model model) {
        Client byLastName = clientService.findClientByLastName(lastName);
        model.addAttribute("byLastName", byLastName);
        return "clientView";
    }

    @GetMapping(value = "/findClientByCnp/{cnp}")
    public String findClientByCnp(@PathVariable(name = "cnp") String cnp, Model model) {
        Client byCnp = clientService.findClientByCnp(cnp);
        model.addAttribute("byCnp", byCnp);
        return "clientView";
    }

    @GetMapping(value = "/all")
    public String findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                          @RequestParam(value = "size", defaultValue = "100") Integer size,
                          ModelMap modelMap) {
        List<Client> clientList = clientService.findAll(page, size);
        modelMap.addAttribute("clientList", clientList);
        return "clientView";
    }



    @RequestMapping(path = "/createClient", method = RequestMethod.GET)
    public String createClientView(ModelMap model) {
        model.addAttribute("client", new Client());
        return "createClient";
    }

    @RequestMapping(path = "/createClient", method = RequestMethod.POST)
    public String createClient(@ModelAttribute Client client) {
        clientService.createClient(client);
        return "redirect:/reservation/createReservation";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateView(ModelMap model, @RequestParam("clientId") Integer clientId) throws Exception {
        Client clientToBeUpdated = clientService.findClientByClientId(clientId);
        model.addAttribute("client", clientToBeUpdated);
        return "updateClient";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ModelMap model, @ModelAttribute Client client) throws UserNotFoundException {
        clientService.updateClient(client.getClientId(), client);
        return "redirect:/clients/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteClient(ModelMap model, @RequestParam("clientId") Integer clientId) throws UserNotFoundException {
        clientService.deleteClient(clientId);
        return "redirect:/clients/all";
    }

}
