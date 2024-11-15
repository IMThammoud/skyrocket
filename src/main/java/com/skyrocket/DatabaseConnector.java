package com.skyrocket;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static com.skyrocket.controller.PageController.LOG;

public class DatabaseConnector{
    static class DBConnector {
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
    }


}
