package com.example.framework.appium.config;

import com.example.framework.appium.exception.AppiumException;
import com.example.framework.appium.util.ConfigLoader;
import com.saucelabs.common.SauceOnDemandAuthentication;
import org.apache.http.client.utils.URIBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class SaucelabsConfig {

    private static final String DEFAULT_SAUCELABS_HOST = "ondemand.saucelabs.com";
    private final String sauceAccessKey;
    private final String sauceUsername;
    private final Properties properties;
    public final SauceOnDemandAuthentication authentication;
    private String buildNumber;

    public SaucelabsConfig() {
        properties = ConfigLoader.loadProperties("appium-saucelabs");
        sauceUsername = properties.getProperty("saucelabs.user.name");
        sauceAccessKey = properties.getProperty("saucelabs.user.accessKey");
        buildNumber = System.getProperty("buildNumber", "");

        authentication = new SauceOnDemandAuthentication(sauceUsername, sauceAccessKey);
    }

    public URL getServerUrl() {
        try {
            return new URIBuilder()
                    .setScheme("http")
                    .setHost(getHost())
                    .setPath("/wd/hub")
                    .setPort(80)
                    .setUserInfo(getUsername(), getAccessKey())
                    .build().toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new AppiumException(e.getMessage());
        }
    }

    public String getUsername() {
        return sauceUsername;
    }

    public String getAccessKey() {
        return sauceAccessKey;
    }

    private String getHost() {
        return properties.getProperty("saucelabs.host", DEFAULT_SAUCELABS_HOST);
    }

    public String getIOSAppPath() {
        return "sauce-storage:" + properties.getProperty("saucelabs.ios.app", "app_build_ios" + buildNumber + ".zip");
    }

    public String getAndroidAppPath() {
        return "sauce-storage:" + properties.getProperty("saucelabs.android.app", "app_build_android" + buildNumber + ".zip");
    }
}
