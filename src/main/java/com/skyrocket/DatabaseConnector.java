package com.skyrocket;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

import static com.skyrocket.controller.PageController.LOG;

public class DatabaseConnector{


    public static class DBConnector {
        private String url;
        private String name;
        private String pw;
        static Properties props = new Properties();
        public static Connection connection;

        public static void connect() {
            try {
                Path pathToEnvs = Paths.get("src/main/java/com/skyrocket/envs.txt");
                InputStream file = Files.newInputStream(pathToEnvs);
                props.load(file);
            }catch (IOException e) {
                System.out.println("Unable to read envs.txt file");
            }
            try {
                connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/"+ props.get("DATABASE_NAME") +"?user="+ props.get("MARIADB_USERNAME") +"&password="+ props.get("MARIADB_PASSWORD"));
                LOG.info("CONNECTION TO DATABASE SUCCESSFUL");
            } catch (Exception e) {
                LOG.info("DATABASE CONNECTION FAILED, REASON: "+ e.getMessage());
            }

        }

        public static void prepareTables(){
            try{
                PreparedStatement statement = connection.prepareStatement("""
                        CREATE TABLE IF NOT EXISTS user_account(
                            pk_id varchar(64) NOT NULL,
                            email varchar(64) NOT NULL,
                            user_password varchar(64) NOT NULL,
                            session_id varchar(100) NOT NULL,
                            created_at timestamp DEFAULT current_timestamp NOT NULL,
                            UNIQUE(email),
                            PRIMARY KEY(pk_id)
                        )""");
                LOG.info("Preparing user_account table.");
                statement.execute();

                statement = connection.prepareStatement("""
                        CREATE TABLE IF NOT EXISTS
                          `shelve` (
                            `pk_shelve_id` varchar(64) NOT NULL,
                            `name` varchar(64) NOT NULL,
                            `category` varchar(100) NOT NULL,
                            `is_for_services` tinyint(1) NOT NULL,
                            `type` varchar(64) NOT NULL,
                            `fk_user_account_id` varchar(100) NOT NULL,
                            PRIMARY KEY (`pk_shelve_id`),
                            CONSTRAINT FK_USER_SHELVE FOREIGN KEY(fk_user_account_id) REFERENCES user_account(pk_id)
                          ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_uca1400_ai_ci""");

                LOG.info("Preparing shelve table.");
                statement.execute();



                LOG.info("PREPARED DATABASE TABLES SUCCESSFULLY");
                statement.close();

        } catch(Exception e){
            LOG.info("Preparing Database-tables failed, REASON: "+ e.getMessage());}
        }
    }


}
