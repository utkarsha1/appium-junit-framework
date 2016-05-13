package com.example.framework.appium.builder;

import com.example.framework.appium.config.AppiumConfig;
import com.example.framework.appium.config.SaucelabsConfig;
import com.example.framework.appium.domain.Device;
import com.example.framework.appium.domain.Platform;
import com.example.framework.appium.domain.PlatformConfig;
import com.example.framework.appium.exception.AppiumException;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DesiredCapabilitiesBuilder {

    public static final String DEVICE_TYPE = "deviceType";
    public static final String PHONE = "phone";
    public static final String SEND_KEY_STRATEGY = "sendKeyStrategy";
    public static final String SET_VALUE = "setValue";
    private static final String ANDROID_APP_PACKAGE = "com.example.android.xyz";
    private static final String ANDROID_LANDING_ACTIVITY = "com.example.xyz.activity.LandingActivity";
    private final SaucelabsConfig saucelabsConfig = new SaucelabsConfig();
    private final AppiumConfig appiumConfig = new AppiumConfig();

    public DesiredCapabilities buildDesiredCapabilitiesAndroid(PlatformConfig platformConfig) {
        DesiredCapabilities caps = buildBaseCapabilities(platformConfig);
        caps.setCapability(MobileCapabilityType.APP, appiumConfig.getAndroidAppPath());
        caps.setCapability(DEVICE_TYPE, PHONE);
        caps.setCapability(MobileCapabilityType.APP_PACKAGE, ANDROID_APP_PACKAGE);
        caps.setCapability(MobileCapabilityType.APP_ACTIVITY, ANDROID_LANDING_ACTIVITY);
        return caps;
    }

    public DesiredCapabilities buildDesiredCapabilitiesIOS(PlatformConfig platformConfig) {
        DesiredCapabilities caps = buildBaseCapabilities(platformConfig);
        caps.setCapability(SEND_KEY_STRATEGY, SET_VALUE);
        caps.setCapability(MobileCapabilityType.APP, appiumConfig.getIOSAppPath());
        return caps;
    }

    public DesiredCapabilities buildDesiredCapabilitiesIOSOnSauce(PlatformConfig platformConfig) {
        DesiredCapabilities caps = buildBaseCapabilities(platformConfig);
        caps.setCapability(SEND_KEY_STRATEGY, SET_VALUE);
        caps.setCapability("waitForAppScript", true);
        caps.setCapability(MobileCapabilityType.APP, saucelabsConfig.getIOSAppPath());
        return caps;
    }

    public DesiredCapabilities buildDesiredCapabilitiesAndroidOnSauce(PlatformConfig platformConfig) {
        DesiredCapabilities caps = buildBaseCapabilities(platformConfig);
        caps.setCapability(DEVICE_TYPE, PHONE);
        caps.setCapability(MobileCapabilityType.APP, saucelabsConfig.getAndroidAppPath());
        return caps;
    }

    private DesiredCapabilities buildBaseCapabilities(PlatformConfig platformConfig) {
        if(platformConfig == null) {
            throw new AppiumException("Platform Config cannot be null in DesiredCapabilitiesBuilder");
        }
        Platform platform = platformConfig.getPlatform();
        Device device = platformConfig.getDevice();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceOrientation", device.getOrientation());
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platform.getVersion());
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platform.getName());
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, device.getName());
        caps.setCapability(MobileCapabilityType.APPIUM_VERSION, appiumConfig.getAppiumVersion());
        caps.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        return caps;
    }
}
