package com.example.framework.appium.config;

import com.google.common.collect.Lists;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.SauceREST;
import com.example.framework.appium.builder.ConcurrentParameterizedHelper;
import com.example.framework.appium.domain.Device;
import com.example.framework.appium.domain.DeviceProvider;
import com.example.framework.appium.domain.Platform;
import com.example.framework.appium.domain.PlatformConfig;
import com.example.framework.appium.rule.Retry;
import com.example.framework.appium.runner.ParallelParameterized;
import com.example.framework.appium.util.SetUpUtil;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assume.*;

/**
 * BaseTest for common setUp and tearDown of
 * a UI Integration Native App automation test
 * using appium. Configured to Run on Saucelabs by default
 * Platform Configs and parameters can be overridden in
 * individual test suites
 */
@RunWith(ParallelParameterized.class)
public abstract class BaseTest implements SauceOnDemandSessionIdProvider {

    private final String platformVersion;
    private final DeviceProvider provider;
    private final String deviceName;
    private final String platformName;
    private String sessionId;

    private MobileDriver driver;

    private final SaucelabsConfig saucelabsConfig = new SaucelabsConfig();
    private final SetUpUtil setUpUtil = new SetUpUtil();
    private static ConcurrentParameterizedHelper concurrentParameterizedHelper = new ConcurrentParameterizedHelper();

    @Rule
    public final TestName name = new TestName() {
        public String getMethodName() {
            return (super.getMethodName() + platformName + platformVersion + deviceName);
        }
    };
    @Rule
    public Retry retry = new Retry(1);
    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, saucelabsConfig.authentication);

    /**
     * Pass the platform to run run tests on
     * tests are run concurrently in parallel
     * @param platformConfig
     */
    protected BaseTest(PlatformConfig platformConfig) {
        this(platformConfig, DeviceProvider.SAUCELABS);
    }

    protected BaseTest(PlatformConfig platformConfig, DeviceProvider provider) {

        this.provider = provider;
        Platform platform = platformConfig.getPlatform();
        this.platformName = platform.getName();
        this.platformVersion = platform.getVersion();
        Device device = platformConfig.getDevice();
        this.deviceName = device.getName();
        /**
         * Line below is a way to conditionally ignore tests.
         * It could be used to ignore unsupported platforms.
         * Currently we use it to run tests against platform specified by the user on bamboo.
         * Default is "assume ignore nothing" and run all tests against all platforms.
         * Based on gradle inputs you can run tests on iOSOnly or androidOnly
         * http://junit.org/apidocs/org/junit/Assume.html
         */
        assumeFalse(setUpUtil.shouldIgnore(platformName));

        // Build desired capabilities based on user specified platform config.
        DesiredCapabilities caps = setUpUtil.buildDesiredCapabilities(platformConfig, provider);
        // Get platform specific WebDriver
        driver = setUpUtil.getPlatformSpecificDriver(caps, provider);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
    }

    /**
     * Default parameters/platforms to run Test Suites on
     */
    @Parameterized.Parameters
    public static List<PlatformConfig[]> simulators() {
        return concurrentParameterizedHelper.getPlatformConfiguration(
                Lists.newArrayList(
                        new PlatformConfig(Platform.ANDROID_5_1, Device.ANDROID_EMULATOR),
                        new PlatformConfig(Platform.IOS_9_2, Device.IPHONE_6_PLUS)
                )
        );
    }

    protected MobileDriver getDriver() {
        return driver;
    }

    @Before
    public void commonSetup() {
        updateTestName();
        setUp();
    }

    @After
    public void commonTearDown() {
        driver.quit();
        tearDown();
    }

    public void setUp() {
        /**
         * Subclasses can override to invoke custom data set-up in suites
         * to be run @Before each testCase
         */
    }
    public void tearDown() {
        /**
         * Subclasses can override to invoke custom tearDown in suites
         * to be run @After each testCase
         */
    }

    public void updateTestName() {
        if (provider.equals(DeviceProvider.SAUCELABS)) {
            SauceREST client = new SauceREST(saucelabsConfig.getUsername(), saucelabsConfig.getAccessKey());
            Map<String, Object> updates = new HashMap<>();
            updates.put("name", name.getMethodName());
            client.updateJobInfo(sessionId, updates);
        }
    }

    protected boolean isAndroid() {
        return (driver instanceof AndroidDriver);
    }

    protected boolean isIOS() {
        return (driver instanceof IOSDriver);
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }
}
