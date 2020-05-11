package com.sda.propertyManager.controller;

import com.sda.propertyManager.model.User;
import com.sda.propertyManager.repository.UserRepository;
import com.sda.propertyManager.service.UserNotFoundException;
import com.sda.propertyManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/findById/{id}")
    public String findById(@PathVariable(name = "id") Integer id, Model model) throws Exception {
        User byId = userService.findById(id);
        model.addAttribute("userById", byId);
        return "userView";
    }
    @GetMapping(value = "/all")
    public String findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                          @RequestParam(value = "size", defaultValue = "100") Integer size,
                          ModelMap modelMap) {
        List<User> userList = userService.findAll(page, size);
        modelMap.addAttribute("userList", userList);
        return "userView";
    }
        @RequestMapping(path = "/createUser", method = RequestMethod.GET)
    public String createUserView(ModelMap model) {
        model.addAttribute("user", new User());
        return "createUser";
    }

    @RequestMapping(path = "/createUser", method = RequestMethod.POST)
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/users/all";
    }
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateView(ModelMap model, @RequestParam("userId") Integer userId) throws Exception {
        User userToBeUpdated = userService.findById(userId);
        model.addAttribute("user", userToBeUpdated);
        return "updateUser";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ModelMap model, @ModelAttribute User user) throws UserNotFoundException {
        userService.updateUser(user.getUserId(), user);
        return "redirect:/users/all";
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUser( @RequestParam("userId") Integer userId) throws UserNotFoundException {
        userService.deleteUser(userId);
        return "redirect:/users/all";
    }

}
