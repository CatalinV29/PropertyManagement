package com.sda.propertyManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    @GetMapping
    public String viewIndexPage() {
        return "indexPage";
    }


    @RequestMapping(value = "/home")
    public class HomeController {

        @GetMapping
        public String homeController() {
            return "homePage";
        }

    }
}