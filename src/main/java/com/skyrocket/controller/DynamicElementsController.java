/*
This Controller manages Requests from Javascript and returns mainly JSON
for things like rendering the shelves of a user dynamically in a table
 */

package com.skyrocket.controller;

import com.skyrocket.model.SessionStore;
import com.skyrocket.model.Shelve;
import com.skyrocket.model.UserAccount;
import com.skyrocket.model.UserSalts;
import com.skyrocket.model.articles.electronics.Notebook;
import com.skyrocket.model.non_entities.FilteredNotebookForPDF;
import com.skyrocket.repository.*;
import com.skyrocket.services.HashingService;
import com.skyrocket.services.PDFCreatorWithOpenPDF;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.skyrocket.controller.PageController.LOG;

@RestController
public class DynamicElementsController {

    private final UserAccountRepository userAccountRepository;
    private final UserSaltsRepository userSaltsRepository;
    private final SessionStoreRepository sessionStoreRepository;
    private final ShelveRepository shelveRepository;
    private final NotebookRepository notebookRepository;

    PDFCreatorWithOpenPDF pdfCreator;

    @Autowired
    public DynamicElementsController(PDFCreatorWithOpenPDF pdfCreator,
                                     UserAccountRepository userAccountRepository,
                                     UserSaltsRepository userSaltsRepository,
                                     SessionStoreRepository sessionStoreRepository,
                                     ShelveRepository shelveRepository,
                                     NotebookRepository notebookRepository
    ) {
        this.pdfCreator = pdfCreator;
        this.userAccountRepository = userAccountRepository;
        this.userSaltsRepository = userSaltsRepository;
        this.sessionStoreRepository = sessionStoreRepository;
        this.shelveRepository = shelveRepository;
        this.notebookRepository = notebookRepository;
    }

    @GetMapping("/category/get/list_of_article_types_based_on_category")
    public List<String> getListOfArticleTypesBasedOnCategoryChosen() {
        return null;
    }

    @PostMapping("/add/article/receiveArticle")
    public String receiveArticle(@CookieValue(name = "JSESSIONID") String sessionId,
                                 @RequestBody Map<String, String> notebook) {
        LOG.info("Received notebook: " + notebook.toString());
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            Shelve shelveForArticle = shelveRepository.findById(UUID.fromString(notebook.get("fk_shelve_id")));
            Notebook newNotebook = new Notebook(UUID.randomUUID(),
                    notebook.get("name"),
                    Integer.parseInt(notebook.get("amount")),
                    notebook.get("type"),
                    notebook.get("description"),
                    Double.parseDouble(notebook.get("price_when_bought")),
                    Double.parseDouble(notebook.get("selling_price")),
                    shelveForArticle,
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

            // This will block shelves with the same name in one shelve.
            //if (notebookRepository.existsNotebookByShelveAndName(shelveRepository.findById(UUID.fromString(notebook.get("fk_shelve_id"))), notebook.get("name"))) {
            //    return "ArticleNameAlreadyExists";
            //}

            LOG.info("Shelve for article: " + shelveForArticle.toString());
            LOG.info("New notebook: " + newNotebook);

            notebookRepository.save(newNotebook);

            return "success";
        } else {
            return "something went wrong with the notebook inserting method";
        }
    }

    @PostMapping("/add/article/check-shelve-type")
    public String renderArticleFormBasedOnShelveType(@CookieValue(name = "JSESSIONID") String sessionId,
                                                     @RequestBody Map<String, String> jsBody) {
        if (sessionStoreRepository.existsBySessionToken(sessionId)) {
            // ShelveId will be carried through option into select element in html
            // check the Shelve_ID and see what type it is
            // Use Type with Switch Case to return type as string so JS can build the form for the type
            // Have to check for is_for_service too so i can render article or Service template <- important
            // Add more switch cases as more types are available (notebook, smartphone, tablet, etc.)
            // Maybe this has to be solved differently as it feels very UngaBunga
            Shelve shelveToBeChecked = shelveRepository.findById(UUID.fromString(jsBody.get("shelve")));
            System.out.println("check-shelve-type Endpoint received this shelve_id: " + jsBody.get("shelve"));
            if (!shelveToBeChecked.getIsForService()) {
                switch (shelveToBeChecked.getType()) {
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

    // When logging in: new sessionStore entry for user is created.
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    public String login(@RequestBody HashMap<String,String> userCredentials,
                        @CookieValue(name = "JSESSIONID", required = false) String cookieAlreadyInUse,
                        HttpSession session) throws NoSuchAlgorithmException {

        // If someone already has a SessionTOKEN then this prevents duplicate SessionTOKENS in the sessionstore.
        if (sessionStoreRepository.existsBySessionToken(cookieAlreadyInUse)) {
            // JS checks for this as response and then lets user to the dashboard.
            return "alreadyHasAccount";
        }

        // Fetch Useraccount and see if its even there.
        UserAccount foundUserAccountByEmail = userAccountRepository.getByEmail(userCredentials.get("email"));

        UserSalts userSalt = userSaltsRepository.getSaltByUserAccount(foundUserAccountByEmail);
        byte[] hashedPassword = HashingService.createPasswordHash(userSalt.getSalt(), userCredentials.get("password"));



        if (foundUserAccountByEmail != null && Arrays.equals(foundUserAccountByEmail.getPassword(), hashedPassword)) {
            SessionStore sessionStore = new SessionStore();
            // Important: Always use the object that was fetched from DB and dont use the parameter Object when referencing.
            sessionStore.setUserAccount(foundUserAccountByEmail);
            sessionStore.setSessionToken(session.getId());

            sessionStoreRepository.save(sessionStore);
            return "true";
        }

        return "false";
    }

    @PostMapping("shelve/shelve-content-to-pdf")
    public ResponseEntity<FileSystemResource> getShelveContentToPDF(@CookieValue(name = "JSESSIONID") String sessionId,
                                                                    @RequestBody Map<String, String> requestBodyContainingShelveId) throws FileNotFoundException {

        Shelve shelve = shelveRepository.findById(UUID.fromString(requestBodyContainingShelveId.get("shelve_id")));
        // Here i should check the Shelve_type and based on that i would create the corresponding PDFCREATOR and call the right PDF methods.
        if (shelveRepository.existsById(shelve.getId()) && notebookRepository.countByShelve_Id(shelve.getId()) > 0) {
            switch (shelve.getType()) {
                case "notebook":
                    FilteredNotebookForPDF filteredNotebookForPDFForLengthOfColumns = new FilteredNotebookForPDF();
                    pdfCreator = new PDFCreatorWithOpenPDF();
                    LOG.info(String.valueOf(filteredNotebookForPDFForLengthOfColumns.getColumnsForTablePDF().size()));
                    LOG.info("Generating PDF for contents of this shelve:" + shelve.getId() + ", And name: " + shelve.getName());
            }

            FileSystemResource createdPdf = new FileSystemResource(pdfCreator.createAndReturnPDFForNotebook(notebookRepository.findByShelve(shelve), shelve));
            // I have to check the ShelveType here first so my PDF-Method knows which
            // structure is needed for the PDF-Template (what columns to use for the table)
            // Example: type=notebook will tell the PDF-Methods that i need the Notebook-Columns and not Smartphone.java ones..
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body(createdPdf);
        } else
            LOG.info("Shelve is empty so no PDF was created for shelve: " + shelve.getId());
        return ResponseEntity.badRequest().body(null);
    }

    // Endpoint for Invoice free-mode
    @PostMapping("/invoice/pdf-freemode")
    public ResponseEntity<FileSystemResource> getInvoiceFreeMode(@CookieValue(name = "JSESSIONID") String sessionId,
                                                                 @RequestBody Map<String, String> invoiceInfo) throws IOException {

        for(var entry : invoiceInfo.entrySet()){
            if ( entry == null || entry.getValue().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
        }

        // Parameter is not used for because i dont use tables for this invoice yet.
        pdfCreator = new PDFCreatorWithOpenPDF();
        FileSystemResource fileSystemResource = new FileSystemResource(pdfCreator.createInvoiceFreeModePDF(invoiceInfo));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(fileSystemResource);
    }

}
