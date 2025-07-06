package com.skyrocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skyrocket.model.Shelve;
import com.skyrocket.model.UserAccount;
import com.skyrocket.model.articles.electronics.Notebook;
import com.skyrocket.model.non_entities.ConvertNotebookListForShelveView;
import com.skyrocket.model.non_entities.FilteredNotebookForShelveView;
import com.skyrocket.repository.NotebookRepository;
import com.skyrocket.repository.SessionStoreRepository;
import com.skyrocket.repository.ShelveRepository;
import com.skyrocket.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.skyrocket.controller.PageController.LOG;

@RestController
public class ShelveController {
    UserAccountRepository userAccountRepository;
    SessionStoreRepository sessionStoreRepository;
    ShelveRepository shelveRepository;
    NotebookRepository notebookRepository;

    @Autowired
    public ShelveController(UserAccountRepository userAccountRepository,
                            SessionStoreRepository sessionStoreRepository,
                            ShelveRepository shelveRepository,
                            NotebookRepository notebookRepository) {
        this.userAccountRepository = userAccountRepository;
        this.sessionStoreRepository = sessionStoreRepository;
        this.shelveRepository = shelveRepository;
        this.notebookRepository = notebookRepository;
    }

    // Return List of Shelves in JSON
    // Serialization happens automatically due to Jacksons Spring Web-Starter Super duper Magic
    @PostMapping("/shelve/retrieve")
    public List<Shelve> getShelves(@CookieValue(name = "JSESSIONID") String sessionId) throws JsonProcessingException {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            UserAccount fetchedUserAccount = userAccountRepository.findById(sessionStoreRepository.findBySessionToken(sessionId).getUserAccount().getId());
            LOG.info("Fetched user account: " + fetchedUserAccount.toString());

            List<Shelve> shelveListOfUser = shelveRepository.findByUserAccount(fetchedUserAccount);
            LOG.info("Fetched shelves list: " + shelveListOfUser.toString());

            return shelveListOfUser;
        }
        LOG.info("No shelves were returned");
        return null;
    }

    // use this for rendering articles on dashboard and for getting articles for automatic invoice creation
    @PostMapping("/shelve/get-notebooks-filtered")
    public List<FilteredNotebookForShelveView> getArticlesInShelve(@CookieValue(name = "JSESSIONID") String sessionId,
                                                                   @RequestBody Map<String, String> shelveId) throws JsonProcessingException {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {

            Shelve fetchedShelve = shelveRepository.findById(UUID.fromString(shelveId.get("shelve_id")));

            ConvertNotebookListForShelveView notebookFilter = new FilteredNotebookForShelveView();
            return notebookFilter.filterOutNotUsedColumnsAndCreateNewListForShelveViewDashboard(notebookRepository.findByShelve(fetchedShelve));

        } else return Collections.emptyList();
    }

    // This returns the bigger unfiltered Notebook List
    // Does not use the filtering Method of filteredNotebook Class
    @PostMapping("/shelve/get-notebooks-unfiltered")
    public List<Notebook> getArticlesInShelveUnfiltered(@CookieValue(name = "JSESSIONID") String sessionId,
                                                        @RequestBody Map<String, String> shelveId) throws JsonProcessingException {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {

            Shelve fetchedShelve = shelveRepository.findById(UUID.fromString(shelveId.get("shelve_id")));

            return notebookRepository.findByShelve(fetchedShelve);

        } else return Collections.emptyList();
    }

    // This returns the value that the "Einträge" column will show on ShelveView
    @PostMapping("/shelve/articleCount")
    public int getArticleCountInShelve(@CookieValue(name = "JSESSIONID") String sessionId,
                                       @RequestBody Map<String, String> shelveIdAndShelveTypeInMap) {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            Shelve shelveToBeChecked = shelveRepository.findById(UUID.fromString(shelveIdAndShelveTypeInMap.get("shelve_id")));
            switch (shelveIdAndShelveTypeInMap.get("shelve_type")) {
                case "notebook":
                    LOG.info("Counting Articles in notebook shelve of shelve:" + shelveIdAndShelveTypeInMap.get("shelve_id"));

                    return notebookRepository.countByShelve_Id(shelveToBeChecked.getId());

                default:
                    LOG.info("Default case was met in switch statement.. breaking");
                    break;
            }
        }
        return 0;
    }

    // Return 200 if deletion was successful
    @Transactional
    @DeleteMapping("/shelve/delete")
    public ResponseEntity deleteShelve(@CookieValue(name = "JSESSIONID") String sessionId,
                                       @RequestBody Map<String, String> sessionIdMap) {
        // If sessionToken exists and there are Articles with that shelve_id then delete all the articles and the shelve on top.
        if (sessionStoreRepository.existsBySessionToken(sessionId) && shelveRepository.existsById(UUID.fromString(sessionIdMap.get("shelve_id")))) {
            System.out.println("Deleting Notebooks of Shelve: " + sessionIdMap.get("shelve_id"));
            notebookRepository.deleteAllByShelve(shelveRepository.findById(UUID.fromString(sessionIdMap.get("shelve_id"))));
            System.out.println("Deleting Shelves with id: " + sessionIdMap.get("shelve_id"));
            shelveRepository.delete(shelveRepository.findById(UUID.fromString(sessionIdMap.get("shelve_id"))));
            return ResponseEntity.ok().build();
        } else {
            LOG.info("Shelve could not be deleted.. Either it does not exist or something else happened.");
            return ResponseEntity.internalServerError().build();
        }
    }
}
