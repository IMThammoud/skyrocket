package com.skyrocket.services;

import com.google.gson.Gson;
import com.skyrocket.model.RetrievedShelves;
import com.skyrocket.model.Shelve;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.skyrocket.DatabaseConnector.DBConnector.connection;
import static com.skyrocket.controller.PageController.LOG;

@Service
public class ShelveQueries {

    // returns value of shelves is_for_service column
    public boolean checkIsForService(String sessionID, String shelveID) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT is_for_services
                    FROM shelve AS A
                    INNER JOIN user_account AS B ON B.pk_id = A.fk_user_account_id
                    where A.pk_shelve_id = ? AND B.session_id = ?
                    """);
            statement.setString(1, shelveID);
            statement.setString(2, sessionID);
            ResultSet resultSet = statement.executeQuery();


            resultSet.next();

            boolean shelveIsForServices = resultSet.getBoolean("is_for_services");
                if (shelveIsForServices) {
                    LOG.info("Shelve is for Service ? : " + shelveIsForServices);
                    return true;
                } else {
                    return false;}
            } catch (Exception e) {
            LOG.info("Could not retrieve is_for_service value of shelve, REASON: " + e.getMessage());
            return false;

        }
    }


    // This is needed to return the right article template
    // The JS-Function loadShelves() will save the ShelveID as value of each Shelve in Shelve-Selection (when adding new article)
    // By pressing the button the form will be sent with the shelveID as value to this method
    public String checkShelveType(String sessionID, String shelveID) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT type
                    FROM shelve AS A
                    INNER JOIN user_account AS B ON B.pk_id = A.fk_user_account_id
                    where A.pk_shelve_id = ? AND B.session_id = ?
                    """);
            statement.setString(1, shelveID);
            statement.setString(2, sessionID);
            ResultSet resultSet = statement.executeQuery();


            resultSet.next();

            String shelveType = resultSet.getString("type");

            if(shelveType != null) {
                LOG.info("Shelve Type : " + shelveType);
                return shelveType;
            }
        return null;
        } catch (Exception e) {
            LOG.info("Could not retrieve type of shelve, REASON: " + e.getMessage());
            return null;

        }
    }

    // Uses sessionID to collect all shelves of the currently logged in User as JSON
    public String getShelvesOfUser(String sessionid){
        try {
            List<RetrievedShelves> shelves = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement("""
                    SELECT aliasShelves.pk_shelve_id 
                    FROM shelves AS aliasShelves
                    INNER JOIN user_account AS aliasuser_account
                    ON aliasuser_account.pk_id = aliasShelves.fk_user_account_id
                    WHERE session_id = ?
                    """);

            statement.setString(1, sessionid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                shelves.add(new RetrievedShelves(rs.getString("name"),
                        rs.getString("pk_shelve_id"),
                        rs.getString("type"),
                        rs.getString("category")));
            }

            Gson gson = new Gson();
            LOG.info("Shelves that a User has with the given SessionID: " + gson.toJson(shelves));

            return gson.toJson(shelves);
        } catch (SQLException e) {
            LOG.info("getShelvesOfUser() failed, REASON:" + e.getMessage());
            return null;
        }
    }

    // The shelve attributes you want to render can be added here before JSON creation
    public String retrieveShelves(String sessionId){
        try{
            ArrayList<RetrievedShelves> resultsList = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement("""
                    SELECT shelveAlias.name, shelveAlias.pk_shelve_id, shelveAlias.type, shelveAlias.category
                    FROM shelve AS shelveAlias
                    INNER JOIN user_account AS user_accountAlias ON user_accountAlias.pk_id = shelveAlias.fk_user_account_id
                    WHERE session_id = ?""");
            statement.setString(1, sessionId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                resultsList.add(new RetrievedShelves(resultSet.getString("name"),
                        resultSet.getString("pk_shelve_id"),
                        resultSet.getString("type"),
                        resultSet.getString("category")));
                LOG.info("Added Shelve to List");
            }

            statement.close();

            Gson gson = new Gson();
            return gson.toJson(resultsList);

        } catch (Exception e) {
            LOG.info("Couldnt retrieve Shelves for user with use of his SessionID, Reason: " + e.getMessage());
            return null;
        }
    }

    public void insertShelve(Shelve shelve, String session) {
        try {
            /*
            At first the id of the logged in user has to be retrieved
            so the new shelve can be linked to the user.
            The same procedure is needed when linking articles or services to Shelves
             */
            PreparedStatement searchForUserID = connection.prepareStatement("""
                    SELECT pk_id from user_account
                    WHERE session_id = ?""");
            searchForUserID.setString(1, session);
            ResultSet results = searchForUserID.executeQuery();

            // Necessary to get the first row
            results.next();
            String UserID = results.getString("pk_id");
            shelve.setFkUserAccount(UserID);

            searchForUserID.close();

            PreparedStatement statement = connection.prepareStatement("INSERT INTO shelve (pk_shelve_id, name, category, is_for_services, type, fk_user_account_id)" +
                    "VALUES (?,?,?,?,?,?)");
            statement.setString(1, shelve.getId().toString());
            statement.setString(2, shelve.getName());
            statement.setString(3, shelve.getCategory());
            statement.setBoolean(4, shelve.getIsForService());
            statement.setString(5, shelve.getType());
            statement.setString(6, UserID);

            statement.execute();

            LOG.info("Shelve inserted successfully.");

            statement.close();

        } catch (SQLException e) {
            LOG.info("Shelve insert failed, REASON: "+ e.getMessage());
        }


    }
}