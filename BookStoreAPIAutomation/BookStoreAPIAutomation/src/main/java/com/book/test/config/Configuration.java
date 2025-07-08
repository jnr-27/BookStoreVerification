package com.book.test.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static final Configuration config = new Configuration();

    private final Properties configProperties = new Properties();

    private Configuration() {
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            configProperties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println(" exception on reading config file and exception is: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getConfigValue(String configKey) {

        return String.valueOf(configProperties.get(configKey));
    }

    public static Configuration getConfiguraions(){
        return config;
    }
}
