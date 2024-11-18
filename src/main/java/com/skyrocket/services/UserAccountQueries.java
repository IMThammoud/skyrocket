package com.skyrocket.services;

import com.skyrocket.model.UserAccount;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.UUID;

import static com.skyrocket.DatabaseConnector.DBConnector.connection;
import static com.skyrocket.controller.PageController.LOG;

@Service
public class UserAccountQueries {
    public void insertUser(UserAccount userAccount) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                
                    INSERT INTO user_account(
                pk_id, email, user_password, session_id)
                VALUES (?,?,?
            ,?)""");
        preparedStatement.setString(1,UUID.randomUUID().toString());
        preparedStatement.setString(2, userAccount.getEmail());
        preparedStatement.setString(3, userAccount.getPassword());
        preparedStatement.setString(4, userAccount.getSessionId());
        preparedStatement.execute();
        LOG.info("Inserted new user successfully");
        } catch (Exception e) {
            LOG.info("Inserting user failed, REASON: "+ e.getMessage());
        }
    }
}
