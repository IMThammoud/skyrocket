/*
This Controller manages Requests from Javascript and returns mainly JSON
for things like rendering the shelves of a user dynamically in a table
 */

package com.skyrocket.controller;

import com.skyrocket.services.ShelveQueries;
import com.skyrocket.services.UserAccountQueries;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

import static com.skyrocket.controller.PageController.LOG;

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
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes="application/json")
    public String login(@RequestBody Map<String,String> fromLoginForm, HttpSession session){
        if(userAccountQueries.userExists(fromLoginForm.get("email"), fromLoginForm.get("password"))){
            userAccountQueries.updatedSessionIdForUser(fromLoginForm.get("email"), fromLoginForm.get("password"), session.getId());
            LOG.info("User logged in with session :"+ session.getId());
            return "true";
        }

        return "false";
    }

}
