package com.sda.propertyManager.controller;

import com.sda.propertyManager.model.Reservation;
import com.sda.propertyManager.service.ClientService;
import com.sda.propertyManager.service.PropertyService;
import com.sda.propertyManager.service.ReservationService;
import com.sda.propertyManager.service.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping (value = "/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ClientService clientService;

    @Autowired
    PropertyService propertyService;

    @GetMapping(value = "/findReservationByReservationId/{id}")
    public String findClientByClientId(@PathVariable(name = "id") Integer id, Model model) throws Exception {
        Reservation byId = reservationService.findReservationByReservationId(id);
        model.addAttribute("reservationById", byId);
        return "reservationView";
    }
    @GetMapping(value = "/all")
    public String findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                          @RequestParam(value = "size", defaultValue = "100") Integer size,
                          ModelMap modelMap) {
        List<Reservation> reservationList = reservationService.findAll(page, size);
        modelMap.addAttribute("reservationList", reservationList);
        return "reservationView";
    }
    @RequestMapping(path = "/createReservation", method = RequestMethod.GET)
    public String createReservationView(ModelMap model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("clientList", clientService.findAll(0,100) );
        model.addAttribute("propertyList", propertyService.findAll(0,100));
        return "createReservation";
    }

    @RequestMapping(path = "/createReservation", method = RequestMethod.POST)
    public String createReservation(@ModelAttribute Reservation reservation) {
        reservationService.createReservation(reservation);
        return "redirect:/reservation/all";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateView(ModelMap model, @RequestParam("reservationId") Integer reservationId) throws Exception {
        Reservation reservationToBeUpdated = reservationService.findReservationByReservationId(reservationId);
        model.addAttribute("reservation", reservationToBeUpdated);
        return "updateReservation";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ModelMap model, @ModelAttribute Reservation reservation) throws UserNotFoundException {
        reservationService.updateReservation(reservation.getReservationId(), reservation);
        return "redirect:/reservation/all";
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteReservation(ModelMap model, @RequestParam("reservationId") Integer reservationId) throws UserNotFoundException {
        reservationService.deleteReservation(reservationId);
        return "redirect:/reservation/all";
    }

}
