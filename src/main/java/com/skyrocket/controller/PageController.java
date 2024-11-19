package com.skyrocket.controller;

import com.skyrocket.model.Shelve;
import com.skyrocket.model.UserAccount;
import com.skyrocket.services.ShelveQueries;
import com.skyrocket.services.UserAccountQueries;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class PageController {

    public PageController() {};

    public final static Logger LOG = LoggerFactory.getLogger(PageController.class);
    Shelve shelve;
    @Autowired
    ShelveQueries shelveQueries;
    @Autowired
    UserAccountQueries userAccountQueries;
    Boolean isForService;
    @Autowired
    HttpSession session;

    @GetMapping("/")
    public String landingPage(){
        return "landing-page";
    }

    @GetMapping("/registration")
    public String registrationPage(){
        return "registration";
    }

    @PostMapping("/register")
    public String register(@RequestParam(name = "email")String email,
                           @RequestParam(name = "password")String password,
                           @RequestParam(name = "password-confirm")String passwordConfirmation
                           ){

        UserAccount newUser = new UserAccount(UUID.randomUUID(), email, password, session.getId());
        if ( userAccountQueries.emailAlreadyExists(email) || userAccountQueries.userExists(email, password)) {
            return "registration";
        }
        LOG.info("Registering new user: " + newUser.toString());
        LOG.info("sessionID of newly created user: " + session.getId());
        userAccountQueries.insertUser(newUser);
        return "registration-successful";
    }

    @GetMapping("/login-page")
    public String loginPage(){
        return "login-page";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "email")String email,
                        @RequestParam(name = "password")String password){
        if(userAccountQueries.userExists(email, password)){
            userAccountQueries.updatedSessionIdForUser(email, password, session.getId());
            LOG.info("User logged in with session :"+ session.getId());
            return "shelves";
        }
        return "login-page";
    }

    @GetMapping("/shelve/shelves")
    public String showShelveDashboard(){
        return "shelves";
    }

    @GetMapping("/shelve/create")
    public String createNewShelve(){
        return "add-shelve";
    }


    @PostMapping("/shelve/submit")
    public String receiveNewShelve(@RequestParam(name = "shelve_name")String shelveName,
                                   @RequestParam(name = "is_for_service")String isForServiceAsString,
                                   @RequestParam(name = "category")String shelveCategory,
                                   @RequestParam(name = "article_selection")String type){
        LOG.info("Received Request.");
        isForService = isForServiceAsString.equals("yes");
        shelve = new Shelve(UUID.randomUUID(), shelveName, shelveCategory, isForService,type, "placeholder");

        shelveQueries.insertShelve(shelve, session);
        // Add Javascript Modal saying added Shelve or not with Thymeleaf Rendering
        return "shelves";
    }
}
