package com.skyrocket;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DatabaseConnector {
    private String url;
    private String name;
    private String pw;

    public static void connect() {
        Properties props = new Properties();
        try {
            Path pathToEnvs = Paths.get("/envs.txt");
            InputStream file = Files.newInputStream(pathToEnvs);
        }catch (IOException e) {
            System.out.println("Unable to read envs.txt file");
        }
    }
}
