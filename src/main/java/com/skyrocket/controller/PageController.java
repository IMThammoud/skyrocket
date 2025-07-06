/*
This Controller handles the Pages that the user sees.
After Login there is a SessionID check for every action to authenticate the User.
A Users SessionID gets invalidated and replaced by a randomUUID on server after Logout
So using the site without logging in again should not be possible/allowed
 */

package com.skyrocket.controller;

import com.skyrocket.model.Shelve;
import com.skyrocket.model.UserAccount;
import com.skyrocket.model.UserSalts;
import com.skyrocket.repository.SessionStoreRepository;
import com.skyrocket.repository.ShelveRepository;
import com.skyrocket.repository.UserAccountRepository;
import com.skyrocket.repository.UserSaltsRepository;
import com.skyrocket.services.HashingService;
import com.skyrocket.services.SaltingService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Controller
public class PageController {
    public final static Logger LOG = LoggerFactory.getLogger(PageController.class);
    UserAccountRepository userAccountRepository;
    UserSaltsRepository userSaltsRepository;
    SessionStoreRepository sessionStoreRepository;
    ShelveRepository shelveRepository;
    Shelve shelve;
    Boolean isForService;
    @Autowired
    HttpSession session;

    public PageController(UserAccountRepository userAccountRepository, UserSaltsRepository userSaltsRepository, SessionStoreRepository sessionStoreRepository, ShelveRepository shelveRepository) {
        this.userAccountRepository = userAccountRepository;
        this.userSaltsRepository = userSaltsRepository;
        this.sessionStoreRepository = sessionStoreRepository;
        this.shelveRepository = shelveRepository;
    }

    @GetMapping("/")
    public String landingPage(@CookieValue(name = "JSESSIONID", required = false) String sessionId) {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            return "redirect:/logout";
        }
        return "landing-page";
    }

    @GetMapping("/registration")
    public String registrationPage(@CookieValue(name = "JSESSIONID", required = false) String sessionId) {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            return "redirect:/logout";
        }
        return "registration";
    }

    @PostMapping("/register")
    public String register(@RequestParam(name = "email") String email,
                           @RequestParam(name = "password") String password) throws NoSuchAlgorithmException {

        if (userAccountRepository.getByEmail(email) != null) {
            return "AccountExistsAlready";
        }

        // add salt and hash the password
        byte[] salt = SaltingService.createSalt();
        byte[] hashedPassword = HashingService.createPasswordHash(salt, password);

        UserAccount userAccount = new UserAccount(email, hashedPassword, LocalDateTime.now());
        userAccount.setId(UUID.randomUUID());

        LOG.info("Registering new user: " + userAccount.getEmail());

        userAccountRepository.save(userAccount);

        UserAccount fetchedUser = userAccountRepository.getByEmail(email);

        UserSalts userSalt = new UserSalts(UUID.randomUUID(), salt, fetchedUser, LocalDateTime.now());
        userSaltsRepository.save(userSalt);


        return "registration-successful";
    }

    @GetMapping("/login-page")
    public String loginPage(@CookieValue(name = "JSESSIONID", required = false) String sessionId) {

        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            return "redirect:/logout";
        }

        return "login-page";
    }

    // If sessionID exists
    @GetMapping("/logout")
    public String logout(@CookieValue(name = "JSESSIONID", required = false) String sessionId) {
        if (sessionId != null && sessionStoreRepository.existsBySessionToken(sessionId)) {
            sessionStoreRepository.delete(sessionStoreRepository.findBySessionToken(sessionId));
        }
        LOG.info("invalidated this userSession on server: " + sessionId);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/shelve/shelves")
    public String showShelveDashboard(@CookieValue(name = "JSESSIONID") String sessionId) {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            LOG.info("Authenticated in Shelve Dashboard endpoint with SessionID");
            LOG.info("Found sessionstore objects based on sessionID " + sessionStoreRepository.getSessionStoresBySessionToken(sessionId).get(0).toString());
            return "shelves";
        }
        return "redirect:/logout";
    }

    @GetMapping("/shelve/create")
    public String createNewShelve(@CookieValue("JSESSIONID") String sessionId) {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            return "add-shelve";
        }
        return "redirect:/logout";
    }

    @GetMapping("/add/article")
    public String addArticle(@CookieValue(name = "JSESSIONID") String sessionId) {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            return "add-article";
        }
        return "redirect:/logout";
    }

    // This needs to stay here as it redirects to another endpoint here that returns HTML
    // This class has @Controller annotation so it returns html. Moving this endpoint to ShelveController will
    // lead to it returning a String instead of redirecting :D
    @PostMapping("/shelve/submit")
    public String receiveNewShelve(@RequestParam(name = "shelve_name") String shelveName,
                                   @RequestParam(name = "is_for_service") String isForServiceAsString,
                                   @RequestParam(name = "category") String shelveCategory,
                                   @RequestParam(name = "article_selection") String type,
                                   @CookieValue(name = "JSESSIONID") String sessionId) {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {

            LOG.info("Received Request.");
            isForService = isForServiceAsString.equals("yes");

            UserAccount fetchedUserAccount = userAccountRepository.findById(sessionStoreRepository.findBySessionToken(sessionId).getUserAccount().getId());
            Shelve shelve = new Shelve(UUID.randomUUID(), shelveName, shelveCategory, isForService, type, fetchedUserAccount);

            shelveRepository.save(shelve);
            return "redirect:/shelve/shelves";
        }
        return "redirect:/logout";
    }

    @GetMapping("/add/article-page")
    public String addArticlePage(@CookieValue(name = "JSESSIONID") String sessionId) {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            return "/electronics/add-notebook";
        }
        return "redirect:/logout";
    }

    @GetMapping("/invoice/dashboard")
    public String showInvoiceDashboard(@CookieValue(name = "JSESSIONID") String sessionId) {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            return "invoice-tab";
        } else return "redirect:/logout";
    }

    @GetMapping("/invoice/invoice-freemode")
    public String showInvoiceTab(@CookieValue(name = "JSESSIONID") String sessionId) {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            return "invoice-dashboard";
        } else return "redirect:/logout";
    }

    @GetMapping("/invoice/invoice_for_article_or_service")
    public String showInvoiceForArticleOrService(@CookieValue(name = "JSESSIONID") String sessionId) {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            return "invoice-for-article-or-service";
        } else return "redirect:/logout";
    }
}
