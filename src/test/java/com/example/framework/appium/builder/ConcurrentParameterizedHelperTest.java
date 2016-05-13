package com.example.framework.appium.builder;

import com.google.common.collect.Lists;
import com.example.framework.appium.domain.Device;
import com.example.framework.appium.domain.Platform;
import com.example.framework.appium.domain.PlatformConfig;
import com.example.framework.appium.exception.AppiumException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ConcurrentParameterizedHelperTest {
private final ConcurrentParameterizedHelper concurrentParameterizedHelper = new ConcurrentParameterizedHelper();

    @Test
    public void when_ConcurrentParameterizedHelper_validInput_thenReturnConcurrentParams() {
        PlatformConfig config_1 = new PlatformConfig(Platform.ANDROID_4_1, Device.SAMSUNG_GALAXY_S4);
        PlatformConfig config_2 = new PlatformConfig(Platform.ANDROID_5_1, Device.ANDROID_EMULATOR);
        PlatformConfig config_3 = new PlatformConfig(Platform.IOS_8_4, Device.IPHONE_4S);
        PlatformConfig config_4 = new PlatformConfig(Platform.IOS_9, Device.IPHONE_6_PLUS);

        List<PlatformConfig[]> actual = concurrentParameterizedHelper.getPlatformConfiguration(
                Lists.newArrayList(
                        config_1,
                        config_2,
                        config_3,
                        config_4
                ));

        List<PlatformConfig[]> expected = Lists.newArrayList(
                new PlatformConfig[]{config_1},
                new PlatformConfig[]{config_2},
                new PlatformConfig[]{config_3},
                new PlatformConfig[]{config_4}
        );

        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < actual.size(); i++) {
            assertEquals(actual.get(i)[0], expected.get(i)[0]);
        }
    }

    @Test (expected = AppiumException.class)
    public void when_ConcurrentParameterizedHelper_inputNull_thenException() {
        concurrentParameterizedHelper.getPlatformConfiguration(null);
    }

    @Test (expected = AppiumException.class)
    public void when_ConcurrentParameterizedHelper_inputEmptu_thenException() {
        concurrentParameterizedHelper.getPlatformConfiguration(Lists.newArrayList());
    }

}