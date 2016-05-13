package com.example.framework.appium.util;

import com.example.framework.appium.builder.DesiredCapabilitiesBuilder;
import com.example.framework.appium.domain.DeviceProvider;
import com.example.framework.appium.domain.Platform;
import com.example.framework.appium.exception.AppiumException;
import com.example.framework.appium.factory.DriverFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class SetUpUtilTest {
    @Mock
    DesiredCapabilitiesBuilder builder;
    // Required for partial mocking to verify gradle's androidOnly/iosOnly property
    @Spy
    SetUpUtil setUpUtil;
    @Mock
    DriverFactory driverFactory;

    @Test (expected = AppiumException.class)
    public void testBuildDesiredCapabilities_nullPlatformConfig() throws Exception {
        setUpUtil.buildDesiredCapabilities(null, DeviceProvider.LOCAL);
    }

    @Test (expected = AppiumException.class)
    public void testGetPlatformSpecificDriver_nullDesiredCaps() throws Exception {
        setUpUtil.getPlatformSpecificDriver(null, DeviceProvider.SAUCELABS);
    }

    @Test (expected = AppiumException.class)
    public void testGetPlatformSpecificDriver_emptyDesiredCaps() throws Exception {
        setUpUtil.getPlatformSpecificDriver(new DesiredCapabilities(), DeviceProvider.LOCAL);
    }

    @Test
    public void testIsIOS_platformIOS() {
        assertTrue(setUpUtil.isIOS(Platform.IOS_8_4.getName()));
    }

    @Test
    public void testIsAndroid_platformAndroid() {
        assertTrue(setUpUtil.isAndroid(Platform.ANDROID_4_2.getName()));
    }

    @Test
    public void testIsIOS_platformAndroid() {
        assertFalse(setUpUtil.isIOS(Platform.ANDROID_4_3.getName()));
    }

    @Test
    public void testIsAndroid_platformIOS() {
        assertFalse(setUpUtil.isAndroid(Platform.IOS_8_4.getName()));
    }

    @Test
    public void testShouldIgnore_platformIOS_runIOSOnly() {
        Mockito.when(setUpUtil.runIOSOnly()).thenReturn(true);
        assertFalse(setUpUtil.shouldIgnore(Platform.IOS_8_4.getName()));
    }

    @Test
    public void testShouldIgnore_platformAndroid_runIOSOnly() {
        Mockito.when(setUpUtil.runIOSOnly()).thenReturn(true);
        assertTrue(setUpUtil.shouldIgnore(Platform.ANDROID_4.getName()));
    }

    @Test
    public void testShouldIgnore_platformIOS_runAndroidOnly() {
        Mockito.when(setUpUtil.runAndroidOnly()).thenReturn(true);
        assertTrue(setUpUtil.shouldIgnore(Platform.IOS_9.getName()));
    }

    @Test
    public void testShouldIgnore_platformAndroid_runAndroidOnly() {
        Mockito.when(setUpUtil.runAndroidOnly()).thenReturn(true);
        assertFalse(setUpUtil.shouldIgnore(Platform.ANDROID_4.getName()));
    }

    @Test
    public void testShouldIgnore_whenConfigOnOppositePlatforms_shouldIgnoreBoth() {
        Mockito.when(setUpUtil.runAndroidOnly()).thenReturn(true);
        assertTrue(setUpUtil.shouldIgnore(Platform.IOS_9.getName()));

        Mockito.when(setUpUtil.runIOSOnly()).thenReturn(true);
        assertTrue(setUpUtil.shouldIgnore(Platform.ANDROID_5_1.getName()));
    }

    @Test
    public void testShouldIgnore_whenConfigOnPlatforms_shouldRunBoth() {
        Mockito.when(setUpUtil.runAndroidOnly()).thenReturn(true);
        assertFalse(setUpUtil.shouldIgnore(Platform.ANDROID_4_3.getName()));

        Mockito.reset(setUpUtil);

        Mockito.when(setUpUtil.runIOSOnly()).thenReturn(true);
        assertFalse(setUpUtil.shouldIgnore(Platform.IOS_8_4.getName()));
    }
    
}