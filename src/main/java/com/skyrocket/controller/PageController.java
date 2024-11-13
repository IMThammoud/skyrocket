package com.skyrocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
    public final static Logger LOG = LoggerFactory.getLogger(PageController.class);

    @GetMapping("/")
    public String landingPage(){
        return "landing-page";
    }

    @GetMapping("/shelve/dashboard")
    public String showShelveDashboard(){
        return "shelve-dashboard";
    }

    @GetMapping("/shelve/create")
    public String createNewShelve(){
        return "add-shelve";
    }

    @PostMapping("/shelve/submit")
    public String receiveNewShelve(@RequestParam(name = "shelve_name")String shelveName,
                                   @RequestParam(name = "is_for_service")String isForService,
                                   @RequestParam(name = "category")String shelveCategory,
                                   @RequestParam(name = "article-selection")String articleType){
    LOG.info("Received Request");

    // Add Javascript Modal saying added Shelve or not with Thymeleaf Rendering
        return "shelve-dashboard";
    }
}
