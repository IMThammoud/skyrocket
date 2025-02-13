// This class connects the Application to the MARIADB-Container and prepares/checks for
// required needed tables on each restart

package com.skyrocket;

import org.apache.juli.logging.Log;

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

    //This static Connector is being used through the whole Project
    public static class DBConnector {
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
                connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/"+ props.get("MARIADB_DATABASE") +"?user="+ props.get("MARIADB_USERNAME") +"&password="+ props.get("MARIADB_PASSWORD"));
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
                        ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_uca1400_ai_ci""");
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
                            `created_at` timestamp DEFAULT current_timestamp NOT NULL,
                            PRIMARY KEY (`pk_shelve_id`),
                            CONSTRAINT FK_USER_SHELVE FOREIGN KEY(fk_user_account_id) REFERENCES user_account(pk_id)
                          ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_uca1400_ai_ci""");

                LOG.info("Preparing shelve table.");
                statement.execute();

                statement = connection.prepareStatement("""
                        create table IF NOT EXISTS notebook(
                          pk_id varchar(64) NOT NULL,
                          fk_shelve_id varchar(64),
                          name varchar(64) NOT NULL,
                          amount smallint NOT NULL,
                          type varchar(64) NOT NULL,
                          description varchar(64),
                          price_when_bought double NOT NULL,
                          selling_price double,
                          brand varchar(64) NOT NULL,
                          model_nr varchar(64),
                          cpu varchar(64) NOT NULL,
                          ram smallint,
                          storage_in_gb smallint,
                          display_size_inches double,
                          operating_system varchar(64) NOT NULL,
                          battery_capacity_health double,
                          keyboard_layout varchar(64),
                          side_note varchar(100),
                          created_at timestamp DEFAULT current_timestamp NOT NULL,
                          PRIMARY KEY (pk_id),
                          FOREIGN KEY (fk_shelve_id) REFERENCES shelve (pk_shelve_id)
                          )
                          ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_uca1400_ai_ci""");

                LOG.info("Preparing notebook table.");
                statement.execute();

                statement = connection.prepareStatement("""
                        create table IF NOT EXISTS notebook_sold(
                          pk_id varchar(64) NOT NULL,
                          fk_notebook_id varchar(64) NOT NULL ,
                          amount smallint NOT NULL,
                          side_note varchar(100),
                          created_at timestamp DEFAULT current_timestamp NOT NULL,
                          PRIMARY KEY (pk_id),
                          FOREIGN KEY (fk_notebook_id) REFERENCES notebook(pk_id)
                          )
                          ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_uca1400_ai_ci""");

                LOG.info("Preparing notebook_sold table.");
                statement.execute();

                LOG.info("PREPARED DATABASE TABLES SUCCESSFULLY");
                statement.close();

        } catch(Exception e){
            LOG.info("Preparing Database-tables failed, REASON: "+ e.getMessage());}
        }
    }


}
