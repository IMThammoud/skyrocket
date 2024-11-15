package com.skyrocket;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
                connection = DriverManager.getConnection(props.get("DATABASE_URL") + ""+ props.get("DATABASE_NAME") +"?user="+ props.get("MARIADB_USERNAME") +"&password="+ props.get("MARIADB_PASSWORD"));
                LOG.info("CONNECTION TO DATABASE SUCCESSFUL");
            } catch (Exception e) {
                LOG.info("DATABASE CONNECTION FAILED, REASON: "+ e.getMessage());
            }

        }

        public static void prepareTables(){
            try{
                connection.prepareStatement("""
                        CREATE TABLE
                          `shelve` (
                            `pk_shelve_id` varchar(64) NOT NULL,
                            `name` varchar(64) NOT NULL,
                            `category` varchar(100) NOT NULL,
                            `is_for_services` tinyint(1) NOT NULL,
                            `fk_user_account_id` varchar(100) NOT NULL,
                            PRIMARY KEY (`pk_shelve_id`)
                          ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_uca1400_ai_ci""");

                LOG.info("PREPARED DATABASE TABLES SUCCESSFULLY");

        } catch(SQLException e){
            LOG.info("Preparing Database-tables failed, REASON: "+ e.getMessage());}
        }
    }


}
