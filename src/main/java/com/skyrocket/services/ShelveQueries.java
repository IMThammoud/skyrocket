package com.skyrocket.services;

import com.skyrocket.model.Shelve;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.skyrocket.DatabaseConnector.DBConnector.connection;
import static com.skyrocket.controller.PageController.LOG;

@Service
public class ShelveQueries {

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