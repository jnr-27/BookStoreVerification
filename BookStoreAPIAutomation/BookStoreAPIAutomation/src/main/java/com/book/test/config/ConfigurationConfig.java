package com.book.test.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationConfig {

    private static Properties configProperties = new Properties();

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            configProperties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println(" exception on reading config file and exception is: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String getConfigValue(String configKey) {

        return String.valueOf(configProperties.get(configKey));
    }


}
