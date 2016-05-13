package com.example.login;

import com.example.framework.appium.config.BaseTest;
import com.example.framework.appium.domain.PlatformConfig;
import com.example.framework.appium.page.Page;
import com.example.xyz.page.login.LandingPage;
import com.example.xyz.page.login.LoginPage;
import org.junit.Test;

public class LoginTest extends BaseTest {

    public LoginTest(PlatformConfig platformConfig) {
        super(platformConfig);
    }

    @Override
    public void setUp() {
    }

    @Test
    public void login() {
        Page.initialize(getDriver(), LandingPage.class).getLandingSignIn().click();
        Page.initialize(getDriver(), LoginPage.class).login("email@example.com", "password");
    }
}
