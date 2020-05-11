package com.sda.propertyManager.controller;
import com.sda.propertyManager.model.User;
import com.sda.propertyManager.model.Role;
import com.sda.propertyManager.repository.RoleRepository;
import com.sda.propertyManager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;


}
