package com.example.framework.appium.util;

import com.example.framework.appium.exception.AppiumException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author utkarsha.padhye
 */
public class ConfigLoader {

    public static Properties loadProperties(String path, String defaultPath) {
        try {
            Properties properties = new Properties();
            InputStream inputStream;
            if (new File(path).exists()) {
                inputStream = new FileInputStream(path);
                properties.load(inputStream);
            } else {
                if (StringUtils.isNotBlank(defaultPath)) {
                    inputStream = new FileInputStream(defaultPath);
                    properties.load(inputStream);
                } else {
                    throw new AppiumException("Could not load properties file for appium");
                }
            }
            return properties;
        } catch (IOException e) {
            throw new AppiumException(e.getMessage());
        }
    }

    public static Properties loadProperties(String path) {
        return loadProperties(path, null);
    }
}
