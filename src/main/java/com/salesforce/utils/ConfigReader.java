package com.salesforce.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/global.properties";

    static {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file: " + e.getMessage());
        }
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public static String getUrl() {
        return properties.getProperty("url");
    }

    public static String getUsername() {
        return decrypt(properties.getProperty("encryptedUsername"));
    }

    public static String getPassword() {
        return decrypt(properties.getProperty("encryptedPassword"));
    }

    private static String decrypt(String encryptedValue) {
        if (encryptedValue == null || encryptedValue.isEmpty()) {
            return "";
        }
        return new String(Base64.getDecoder().decode(encryptedValue));
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
