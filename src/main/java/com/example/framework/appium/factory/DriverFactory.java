package com.example.framework.appium.factory;

import com.example.framework.appium.config.AWSDeviceFarmConfig;
import com.example.framework.appium.config.AppiumConfig;
import com.example.framework.appium.config.SaucelabsConfig;
import com.example.framework.appium.exception.AppiumException;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private final SaucelabsConfig saucelabsConfig = new SaucelabsConfig();
    private final AppiumConfig appiumConfig = new AppiumConfig();
    private final AWSDeviceFarmConfig awsDeviceFarmConfig = new AWSDeviceFarmConfig();

    public AndroidDriver getAndroidDriver(DesiredCapabilities caps) {
        return getManagedDriver(new AndroidDriver<>(appiumConfig.getAndroidServerUrl(), caps));
    }

    public AndroidDriver getAndroidDriverOnSauce(DesiredCapabilities caps) {
        return getManagedDriver(new AndroidDriver(saucelabsConfig.getServerUrl(), caps));
    }

    public AndroidDriver getAndroidDriverOnAWS(DesiredCapabilities caps) {
        return getManagedDriver(new AndroidDriver<>(awsDeviceFarmConfig.getServerUrlAndroid(), caps));
    }

    public IOSDriver getIOSDriver(DesiredCapabilities caps) {
        return getManagedDriver(new IOSDriver(appiumConfig.getIOSServerUrl(), caps));
    }

    public IOSDriver getIOSDriverOnSauce(DesiredCapabilities caps) {
        return getManagedDriver(new IOSDriver(saucelabsConfig.getServerUrl(), caps));
    }

    public IOSDriver getIOSDriverOnAWS(DesiredCapabilities caps) {
        return getManagedDriver(new IOSDriver(awsDeviceFarmConfig.getServerUrlIOS(), caps));
    }

    private <T extends MobileDriver> T getManagedDriver(T driver) {
        try {
            return driver;
        } catch (UnsupportedCommandException e) {
            logger.info("Saucelabs platform configs: https://docs.saucelabs.com/reference/platforms-configurator/#/");
            throw new AppiumException(e);
        }
    }
}
