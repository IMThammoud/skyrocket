package com.skyrocket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynamicElementsController {

    @GetMapping("/testjs")
    public String testjs(){
        return "This was returned from testJS endpoint";
    }

}
