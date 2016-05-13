package com.example.framework.appium.config;

import com.example.framework.appium.util.ConfigLoader;
import org.apache.http.client.utils.URIBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class AppiumConfig {

    private static final String DEFAULT_APPIUM_VERSION = "1.4.16";
    private final int androidServerPort;
    private final int iOSServerPort;
    private final String iOSAppPath;
    private final String androidAppPath;
    private final Properties properties;

    public AppiumConfig() {
        properties = ConfigLoader.loadProperties("src/main/resources/appium-local.properties",
                "src/main/resources/appium-local-default.properties");

        androidServerPort = Integer.parseInt(properties.getProperty("android.server.port"));
        iOSServerPort = Integer.parseInt(properties.getProperty("ios.server.port"));
        iOSAppPath = properties.getProperty("ios.app");
        androidAppPath = properties.getProperty("android.app");
    }

    public URL getAndroidServerUrl() {
        try {
            return getBaseBuilder()
                    .setPort(androidServerPort)
                    .build()
                    .toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private URIBuilder getBaseBuilder() {
        return new URIBuilder()
                .setScheme("http")
                .setHost("0.0.0.0")
                .setPath("/wd/hub");
    }

    public URL getIOSServerUrl() {
        try {
            return getBaseBuilder()
                    .setPort(iOSServerPort)
                    .build()
                    .toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getIOSAppPath() {
        return iOSAppPath;
    }

    public String getAndroidAppPath() {
        return androidAppPath;
    }

    public String getAppiumVersion() {
        return properties.getProperty("appium.version", DEFAULT_APPIUM_VERSION);
    }
}
