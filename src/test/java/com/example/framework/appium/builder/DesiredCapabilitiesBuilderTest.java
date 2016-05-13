package com.example.framework.appium.builder;

import com.example.framework.appium.domain.Device;
import com.example.framework.appium.domain.Platform;
import com.example.framework.appium.domain.PlatformConfig;
import com.example.framework.appium.exception.AppiumException;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.*;

public class DesiredCapabilitiesBuilderTest {

    public static final String EXPECTED_APPIUM_VERSION = "1.4.16";
    private static final String ANDROID_APP_PACKAGE = "com.example.android.xyz";
    private static final String ANDROID_LANDING_ACTIVITY = "com.example.xyz.activity.LandingActivity";
    private static final String ANDROID_APP = "/some/location/android";
    private static final String IOS_APP = "some/location/ios";
    private static final String ANDROID_APP_SAUCE = "sauce-storage:app_build_android.zip";
    private static final String IOS_APP_SAUCE = "sauce-storage:app_build_ios.zip";
    private final DesiredCapabilitiesBuilder builder = new DesiredCapabilitiesBuilder();
    private DesiredCapabilities expectedAndroidBase;
    private DesiredCapabilities expectedIOSBase;

    @Before
    public void setUp() throws Exception {
        expectedAndroidBase = new DesiredCapabilities();
        expectedAndroidBase.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        expectedAndroidBase.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        expectedAndroidBase.setCapability("deviceOrientation", "portrait");
        expectedAndroidBase.setCapability("deviceType", "phone");
        expectedAndroidBase.setCapability(MobileCapabilityType.APPIUM_VERSION, EXPECTED_APPIUM_VERSION);

        expectedIOSBase = new DesiredCapabilities();
        expectedIOSBase.setCapability(MobileCapabilityType.APPIUM_VERSION, EXPECTED_APPIUM_VERSION);
        expectedIOSBase.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        expectedIOSBase.setCapability("deviceOrientation", "portrait");
        expectedIOSBase.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        expectedIOSBase.setCapability("sendKeyStrategy", "setValue");
    }

    @After
    public void tearDown() throws Exception {
        expectedAndroidBase = new DesiredCapabilities();
        expectedIOSBase = new DesiredCapabilities();
    }

    @Test
    public void testBuildDesiredCapabilitiesAndroidLocal() throws Exception {
        DesiredCapabilities actual = builder.buildDesiredCapabilitiesAndroid(new PlatformConfig(Platform.ANDROID_4, Device.HTC_ONE_X));
        actual.setCapability(MobileCapabilityType.APP, ANDROID_APP);

        expectedAndroidBase.setCapability(MobileCapabilityType.APP, ANDROID_APP);
        expectedAndroidBase.setCapability(MobileCapabilityType.APP_PACKAGE, ANDROID_APP_PACKAGE);
        expectedAndroidBase.setCapability(MobileCapabilityType.APP_ACTIVITY, ANDROID_LANDING_ACTIVITY);
        expectedAndroidBase.setCapability(MobileCapabilityType.PLATFORM_VERSION, Platform.ANDROID_4.getVersion());
        expectedAndroidBase.setCapability(MobileCapabilityType.DEVICE_NAME, Device.HTC_ONE_X.getName());
        assertEquals(expectedAndroidBase, actual);
    }

    @Test (expected = AppiumException.class)
    public void testBuildDesiredCapabilitiesAndroidLocal_null() throws Exception {
        builder.buildDesiredCapabilitiesAndroid(null);
    }

    @Test
    public void testBuildDesiredCapabilitiesIOSLocal() throws Exception {
        DesiredCapabilities actual = builder.buildDesiredCapabilitiesIOS(new PlatformConfig(Platform.IOS_8_4, Device.IPHONE_5));
        actual.setCapability(MobileCapabilityType.APP, IOS_APP);

        expectedIOSBase.setCapability(MobileCapabilityType.PLATFORM_VERSION, Platform.IOS_8_4.getVersion());
        expectedIOSBase.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.IOS_8_4.getName());
        expectedIOSBase.setCapability(MobileCapabilityType.DEVICE_NAME, Device.IPHONE_5.getName());
        expectedIOSBase.setCapability(MobileCapabilityType.APP, IOS_APP);
        assertEquals(expectedIOSBase, actual);
    }

    @Test
    public void testBuildDesiredCapabilitiesIOSOnSauce() throws Exception {
        DesiredCapabilities actual = builder.buildDesiredCapabilitiesIOSOnSauce(new PlatformConfig(Platform.IOS_9, Device.IPHONE_5S));

        expectedIOSBase.setCapability(MobileCapabilityType.PLATFORM_VERSION, Platform.IOS_9.getVersion());
        expectedIOSBase.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.IOS_9.getName());
        expectedIOSBase.setCapability(MobileCapabilityType.DEVICE_NAME, Device.IPHONE_5S.getName());
        expectedIOSBase.setCapability("waitForAppScript", true);
        expectedIOSBase.setCapability(MobileCapabilityType.APP, IOS_APP_SAUCE);
        assertEquals(expectedIOSBase, actual);
    }

    @Test
    public void testBuildDesiredCapabilitiesAndroidOnSauce() throws Exception {
        DesiredCapabilities actual = builder.buildDesiredCapabilitiesAndroidOnSauce(new PlatformConfig(Platform.ANDROID_5, Device.SAMSUNG_GALAXY_NOTE));
        expectedAndroidBase.setCapability(MobileCapabilityType.APP, ANDROID_APP_SAUCE);
        expectedAndroidBase.setCapability(MobileCapabilityType.PLATFORM_VERSION, Platform.ANDROID_5.getVersion());
        expectedAndroidBase.setCapability(MobileCapabilityType.DEVICE_NAME, Device.SAMSUNG_GALAXY_NOTE.getName());
        assertEquals(expectedAndroidBase, actual);
    }
}