package com.skyrocket.services;

import com.skyrocket.model.Shelve;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.skyrocket.DatabaseConnector.DBConnector.connection;
import static com.skyrocket.controller.PageController.LOG;

@Service
public class ShelveQueries {

    public void insertShelve(Shelve shelve){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO shelve (pk_shelve_id, name, category, is_for_services, fk_user_account_id)" +
                    "VALUES (?,?,?,?,?)");
            statement.setString(1,shelve.getId().toString());
            statement.setString(2,shelve.getName());
            statement.setString(3,shelve.getCategory());
            statement.setBoolean(4,shelve.getIsForService());
            statement.setString(5,"placeholder");

            statement.execute();
            LOG.info("Shelve inserted successfully.");

        } catch (SQLException e) {
            LOG.info("Shelve insert failed, REASON: " + e.getMessage());
        }


    }
}