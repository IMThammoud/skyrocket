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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class PageController {


    public final static Logger LOG = LoggerFactory.getLogger(PageController.class);
    Shelve shelve;
    @Autowired
    ShelveQueries shelveQueries;
    @Autowired
    UserAccountQueries userAccountQueries;
    Boolean isForService;
    @Autowired
    HttpSession session;
    @Autowired
    public PageController(UserAccountQueries userAccountQueries, ShelveQueries shelveQueries) {
        this.userAccountQueries = userAccountQueries;
        this.shelveQueries = shelveQueries;
    }

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
        if ( userAccountQueries.emailAlreadyExists(email)) {
            return "redirect:/registration";
        }
        LOG.info("Registering new user: " + newUser.toString());
        LOG.info("sessionID of newly created user: " + session.getId());
        userAccountQueries.insertUser(newUser);

        userAccountQueries.deleteUserSessionId(session.getId());
        LOG.info("Changed Session_ID after registration to avoid login skip.");
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
            return "redirect:/shelve/shelves";
        }
        return "login-page";
    }
    @GetMapping("/logout")
    public String logout(){
        userAccountQueries.deleteUserSessionId(session.getId());
        session.invalidate();
        LOG.info("invalidated the userSession on server");
        return "redirect:/";
    }

    @GetMapping("/shelve/shelves")
    public String showShelveDashboard(@CookieValue(name = "JSESSIONID") String sessionId){
        if (userAccountQueries.checkSessionId(sessionId)) {
            return "shelves";
        }
        return "redirect:/logout";
    }

    @GetMapping("/shelve/create")
    public String createNewShelve(@CookieValue("JSESSIONID") String sessionId){
        if (userAccountQueries.checkSessionId(sessionId)) {
            return "add-shelve";
        }
        return "redirect:/logout";
    }

    @GetMapping("/add/article")
    public String addArticle(@CookieValue(name = "JSESSIONID") String sessionId){
        if (userAccountQueries.checkSessionId(sessionId)) {
            return "add-article";
        }
        return "redirect:/logout";
    }


    @PostMapping("/shelve/submit")
    public String receiveNewShelve(@RequestParam(name = "shelve_name")String shelveName,
                                   @RequestParam(name = "is_for_service")String isForServiceAsString,
                                   @RequestParam(name = "category")String shelveCategory,
                                   @RequestParam(name = "article_selection")String type,
                                   @CookieValue(name = "JSESSIONID") String sessionId){
        if (userAccountQueries.checkSessionId(sessionId)) {

            LOG.info("Received Request.");
            isForService = isForServiceAsString.equals("yes");
            shelve = new Shelve(UUID.randomUUID(), shelveName, shelveCategory, isForService, type, "placeholder");

            shelveQueries.insertShelve(shelve, sessionId);
            // Add Javascript Modal saying added Shelve or not with Thymeleaf Rendering
            return "redirect:/shelve/shelves";
        }
        return "redirect:/logout";
    }
}
