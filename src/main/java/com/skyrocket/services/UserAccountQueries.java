package com.skyrocket.services;

import com.skyrocket.model.UserAccount;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

import static com.skyrocket.DatabaseConnector.DBConnector.connection;
import static com.skyrocket.controller.PageController.LOG;

@Service
public class UserAccountQueries {

    public void deleteUserSessionId(String sessionId){
        try {
            PreparedStatement statement = connection.prepareStatement("""
                    UPDATE user_account
                    SET session_id = ?
                    WHERE session_id = ?""");
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, sessionId);

            if(statement.executeUpdate()==1){
                LOG.info(sessionId+" Was removed and replaced");
                LOG.info("UserSessionID invalidated on Logout and replaced with random UUID.");
            };
        } catch (Exception e) {
            LOG.info("UserSessionID could not be deleted on Logout, REASON: " + e.getMessage());
        }
    }

    public boolean checkSessionId(String sessionId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT session_id
                FROM user_account
                WHERE session_id = ?""");
            preparedStatement.setString(1, sessionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getString("session_id").equals(sessionId)) {
                LOG.info("SessionId check successful");
                return true;
            }
        } catch(Exception e){
            LOG.info("SessionId check failed, Reason: " + e.getMessage());
            return false;
        }
        LOG.info("SessionId check failed");
        return false;
    }

    public Boolean userExists(String email, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
                SELECT email, user_password
                FROM user_account
                WHERE email = ? AND user_password = ?""");

            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();
            results.next();

            if (results.getString("email").equals(email) && results.getString("user_password").equals(password)) {
                LOG.info("User exists");
                statement.close();
                return true;
            }


        } catch (Exception e) {
            LOG.info("User not found, REASON: " + e.getMessage());
        }
        return false;
    }

    public Boolean emailAlreadyExists(String email) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
                SELECT email
                FROM user_account
                WHERE email = ?""");

            statement.setString(1, email);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                if (results.getString("email").equals(email)) {
                    LOG.info("Email already exists");
                    statement.close();
                    return true;
                }

            }


        } catch (Exception e) {
            LOG.info("User not found, REASON: " + e.getMessage());
        }
        return false;
    }

    public boolean updatedSessionIdForUser(String email, String password, String sessionId) {
        try {
            PreparedStatement statement = connection.prepareStatement("""
                
                UPDATE user_account
                SET session_id = ?
                WHERE email=? AND user_password=?""");

            statement.setString(1, sessionId);
            statement.setString(2, email);
            statement.setString(3, password);

            if (statement.executeUpdate() == 1 ) {
                LOG.info("SessionID was overwritten for user because of login.");
                statement.close();
                return true;
            }
        } catch (Exception e) {
            LOG.info("Overwriting SessionID for User FAILED, REASON: " + e.getMessage());
        }
        return false;
    }

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

        preparedStatement.close();
        } catch (Exception e) {
            LOG.info("Inserting user failed, REASON: "+ e.getMessage());
        }
    }
}
