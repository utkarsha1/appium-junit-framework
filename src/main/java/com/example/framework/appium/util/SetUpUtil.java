package com.example.framework.appium.util;

import com.example.framework.appium.domain.Platform;
import com.example.framework.appium.domain.PlatformConfig;
import com.example.framework.appium.exception.AppiumException;
import com.example.framework.appium.domain.DeviceProvider;
import com.example.framework.appium.builder.DesiredCapabilitiesBuilder;
import com.example.framework.appium.factory.DriverFactory;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Set-up util to configure Test Suites
 * Used in BaseTest to build required desired capabilities
 * And platform specific driver based on platform config. specified
 * In Test Suite
 */
public class SetUpUtil {

    private final DesiredCapabilitiesBuilder desiredCapabilitiesBuilder = new DesiredCapabilitiesBuilder();
    private final DriverFactory driverFactory = new DriverFactory();

    public DesiredCapabilities buildDesiredCapabilities(PlatformConfig platformConfig, DeviceProvider provider) {
        if (platformConfig == null) {
            throw new AppiumException("PlatformConfig was null");
        }
        Platform platform = platformConfig.getPlatform();
        if(provider.equals(DeviceProvider.LOCAL)) {
            return getDesiredCapabilities(platformConfig, platform);
        } else if (provider.equals(DeviceProvider.SAUCELABS)){
            return getDesiredCapabilitiesSauce(platformConfig, platform);
        } else if(provider.equals(DeviceProvider.AWS_DEVICE_FARM)) {
            return new DesiredCapabilities();
        } else {
            throw new AppiumException("Could not determine run on platform");
        }
    }

    private DesiredCapabilities getDesiredCapabilitiesSauce(PlatformConfig platformConfig, Platform platform) {
        if (isIOS(platform.getName())) {
            return desiredCapabilitiesBuilder.buildDesiredCapabilitiesIOSOnSauce(platformConfig);
        } else if (isAndroid(platform.getName())) {
            return desiredCapabilitiesBuilder.buildDesiredCapabilitiesAndroidOnSauce(platformConfig);
        } else {
            throw new AppiumException("Could not determine platform");
        }
    }

    private DesiredCapabilities getDesiredCapabilities(PlatformConfig platformConfig, Platform platform) {
        if (isIOS(platform.getName())) {
            return desiredCapabilitiesBuilder.buildDesiredCapabilitiesIOS(platformConfig);
        } else if (isAndroid(platform.getName())) {
            return desiredCapabilitiesBuilder.buildDesiredCapabilitiesAndroid(platformConfig);
        } else {
            throw new AppiumException("Could not determine platform");
        }
    }


    public MobileDriver getPlatformSpecificDriver(DesiredCapabilities caps, DeviceProvider provider) {
        if (caps == null) {
            throw new AppiumException("DesiredCapabilities null, cannot load appropriate driver");
        }
        if (provider.equals(DeviceProvider.LOCAL)) {
            return getLocalDriver(caps);
        } else if (provider.equals(DeviceProvider.AWS_DEVICE_FARM)){
            return getAWSDriver(caps);
        } else if (provider.equals(DeviceProvider.SAUCELABS)){
            return getSauceDriver(caps);
        } else {
            throw new AppiumException("Could not determine device provider");
        }
    }

    private MobileDriver getLocalDriver(DesiredCapabilities caps) {
        if (isIOS(String.valueOf(caps.getCapability(MobileCapabilityType.PLATFORM_NAME)))) {
            return driverFactory.getIOSDriver(caps);
        } else if (isAndroid(String.valueOf(caps.getCapability(MobileCapabilityType.PLATFORM_NAME)))) {
            return driverFactory.getAndroidDriver(caps);
        } else {
            throw new AppiumException("Could not determine platform");
        }
    }


    /**
     * Currently per AWS requirement, cap = new DesiredCapabilities()
     * AWS implicitly defines them according to their requirements
     * @param caps
     * @return
     */
    private MobileDriver getAWSDriver(DesiredCapabilities caps) {
        if (isIOS(String.valueOf(caps.getCapability(MobileCapabilityType.PLATFORM_NAME)))) {
            return driverFactory.getIOSDriverOnAWS(caps);
        } else if (isAndroid(String.valueOf(caps.getCapability(MobileCapabilityType.PLATFORM_NAME)))) {
            return driverFactory.getAndroidDriverOnAWS(caps);
        } else {
            throw new AppiumException("Could not determine platform");
        }
    }

    private MobileDriver getSauceDriver(DesiredCapabilities caps) {
        if (isIOS(String.valueOf(caps.getCapability(MobileCapabilityType.PLATFORM_NAME)))) {
            return driverFactory.getIOSDriverOnSauce(caps);
        } else if (isAndroid(String.valueOf(caps.getCapability(MobileCapabilityType.PLATFORM_NAME)))) {
            return driverFactory.getAndroidDriverOnSauce(caps);
        } else {
            throw new AppiumException("Could not determine platform");
        }
    }

    public boolean isIOS(String platformName) {
        return StringUtils.containsIgnoreCase(platformName, "IOS");
    }

    public boolean isAndroid(String platformName) {
        return StringUtils.containsIgnoreCase(platformName, "Android");
    }

    public boolean runIOSOnly() {
        return Boolean.parseBoolean(System.getProperty("iOSOnly"));
    }

    public boolean runAndroidOnly() {
        return Boolean.parseBoolean(System.getProperty("androidOnly"));
    }

    /**
     *  Conditionally ignore tests based on user input
     * ./gradlew integrationTest : Run all tests against all platforms configured (currently android and iOS), default build from properties
     * ./gradlew integrationTest -PiOSOnly -PbuildNumber=25 : Run all tests only on iOS against specified build number
     * ./gradlew integrationTest -PandroidOnly -PbuildNumber=25: Run all tests only on android against specified build number
     */
    public boolean shouldIgnore(String platformName) {
        return runAndroidOnly() && isIOS(platformName) || (runIOSOnly() && isAndroid(platformName));
    }


}
