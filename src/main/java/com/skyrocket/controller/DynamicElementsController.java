/*
This Controller manages Requests from Javascript and returns mainly JSON
for things like rendering the shelves of a user dynamically in a table
 */

package com.skyrocket.controller;

import com.skyrocket.services.ShelveQueries;
import com.skyrocket.services.UserAccountQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class DynamicElementsController {

    @Autowired
    private final UserAccountQueries userAccountQueries;
    @Autowired
    private final ShelveQueries shelveQueries;

    public DynamicElementsController(UserAccountQueries userAccountQueries, ShelveQueries shelveQueries) {
        this.userAccountQueries = userAccountQueries;
        this.shelveQueries = shelveQueries;
    }

    @GetMapping("/testjs")
    public String testjs(){
        return "This was returned from testJS endpoint";
    }

    @PostMapping("/shelve/retrieve")
    public String getShelves(@CookieValue(name ="JSESSIONID") String sessionId){
        if (userAccountQueries.checkSessionId(sessionId)) {
            String retrievedShelves = shelveQueries.retrieveShelves(sessionId);
            return retrievedShelves;
        }
        return null;
    }

}
