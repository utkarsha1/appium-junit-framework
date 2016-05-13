package com.example.framework.appium.config;

import com.example.framework.appium.exception.AppiumException;
import org.apache.http.client.utils.URIBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class AWSDeviceFarmConfig {

    public URL getServerUrlIOS() {
        try {
            return new URIBuilder()
                    .setScheme("http")
                    // host defined for AWS
                    .setHost("0.0.0.0")
                    .setPath("/wd/hub")
                    .setPort(4723)
                    .build().toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new AppiumException(e.getMessage());
        }
    }

    public URL getServerUrlAndroid() {
        try {
            return new URIBuilder()
                    .setScheme("http")
                    // host defined for AWS
                    .setHost("localhost")
                    .setPath("/wd/hub")
                    .setPort(4723)
                    .build().toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new AppiumException(e.getMessage());
        }
    }
}
