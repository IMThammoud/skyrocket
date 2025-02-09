/*
This Controller manages Requests from Javascript and returns mainly JSON
for things like rendering the shelves of a user dynamically in a table
 */

package com.skyrocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skyrocket.model.articles.Notebook;
import com.skyrocket.services.ArticleQueries;
import com.skyrocket.services.JsonMethods;
import com.skyrocket.services.ShelveQueries;
import com.skyrocket.services.UserAccountQueries;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

import static com.skyrocket.controller.PageController.LOG;

@RestController
public class DynamicElementsController {

    private final UserAccountQueries userAccountQueries;
    private final ShelveQueries shelveQueries;
    private final ArticleQueries articleQueries;
    JsonMethods jsonMethods;

    public DynamicElementsController(UserAccountQueries userAccountQueries, ShelveQueries shelveQueries) {
        this.userAccountQueries = userAccountQueries;
        this.shelveQueries = shelveQueries;
        this.articleQueries = new ArticleQueries();
    }

    @GetMapping("/testjs")
    public String testjs(){
        return "This was returned from testJS endpoint";
    }

    @PostMapping("/shelve/articleCount")
    public int getArticleCountInShelve(@CookieValue(name = "JSESSIONID") String sessionId,
                                          @RequestBody Map<String, String> shelveIdAndShelveTypeInMap) {
        if (userAccountQueries.checkSessionId(sessionId)) {
            switch (shelveIdAndShelveTypeInMap.get("shelve_type")){
                case "notebook":
                    LOG.info("Counting Articles in notebook shelve of shelve:"+ shelveIdAndShelveTypeInMap.get("shelve_id"));
                    return articleQueries.getArticleCountInShelveIfTypeNotebook(shelveIdAndShelveTypeInMap.get("shelve_id"));

                default:
                    LOG.info("Default case was met in switch statement.. breaking");
                    break;
            }
        }
        return 0;
    }

    @PostMapping("/add/article/receiveArticle")
    public String receiveArticle(@CookieValue(name = "JSESSIONID") String cookie,
                                 @RequestBody Map<String,String> notebook) {
        LOG.info("Received notebook: " + notebook.toString());
        if (userAccountQueries.checkSessionId(cookie)) {
            Notebook newNotebook = new Notebook(UUID.randomUUID(),
                    notebook.get("name"),
                    Integer.parseInt(notebook.get("amount")),
                    notebook.get("type"),
                    notebook.get("description"),
                    Double.parseDouble(notebook.get("price_when_bought")),
                    Double.parseDouble(notebook.get("selling_price")),
                    UUID.fromString(notebook.get("fk_shelve_id")),
                    notebook.get("brand"),
                    notebook.get("model_nr"),
                    notebook.get("cpu"),
                    Integer.parseInt(notebook.get("ram")),
                    Integer.parseInt(notebook.get("storage_in_gb")),
                    Integer.parseInt(notebook.get("display_size_inches")),
                    notebook.get("operating_system"),
                    Integer.parseInt(notebook.get("battery_capacity_health")),
                    notebook.get("keyboard_layout"),
                    notebook.get("side_note")
                    );
            articleQueries.insertNotebook(newNotebook, cookie, newNotebook.getShelveIdAsForeignKey().toString());
            return "success";
        } else {
            return "something went wrong with the notebook inserting method";
        }
    }

    @PostMapping("/add/article/check-shelve-type")
    public String renderArticleFormBasedOnShelveType(@CookieValue(name = "JSESSIONID") String sessionid,
                                                     @RequestBody Map<String, String> jsBody) {
        if(userAccountQueries.checkSessionId(sessionid)) {
            // ShelveId will be carried through option into select element in html
            // check the Shelve_ID and see what type it is
            // Use Type with Switch Case to return right Template
            // Have to check for is_for_service too so i can render article or Service template <- important
            // Add more switch cases as more types are available (notebook, smartphone, tablet, etc.)
            if(shelveQueries.checkIsForService(sessionid, jsBody.get("shelve")) != true) {
                switch (shelveQueries.checkShelveType(sessionid, jsBody.get("shelve"))) {
                    case "notebook":
                        // Returning notebook as string to JS so it can render notebook form
                        return "notebook";

                    case "smartphone":
                        return "smartphone";
                    default:
                        return "redirect:/logout";
                }
            }
        }
        return "redirect:/logout";
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

    // Return 200 if deletion was successful
    @DeleteMapping("/shelve/delete")
    public ResponseStatus deleteShelve(@CookieValue(name = "JSESSIONID") String cookie,
                                       String shelveId) {
        if (userAccountQueries.checkSessionId(cookie)) {

        }
        return null;
    }

    @PostMapping("/shelve/get-articles")
    public String getArticlesInShelve(@CookieValue(name = "JSESSIONID") String sessionId,
                                      @RequestBody Map<String, String> shelveId) throws JsonProcessingException {
        if (userAccountQueries.checkSessionId(sessionId)) {
            jsonMethods = new JsonMethods();
            return jsonMethods.StringifyListOfNotebooks(articleQueries.getArticlesFromShelve(sessionId, shelveId.get("shelve_id")));
        } else return "empty";
    }

}
