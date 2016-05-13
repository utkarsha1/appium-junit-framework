package com.example.xyz.page.login;

import com.example.framework.appium.page.Page;
import com.example.xyz.page.AppPage;
import com.example.xyz.page.NextPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sample login page for logging into native app under test
 */
public class LoginPage extends AppPage {

    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    @AndroidFindBy(id = ANDROID_APP_ID + ":id/email")
    @iOSFindBy(name = "email")
    private MobileElement emailBox;

    @AndroidFindBy(id = ANDROID_APP_ID + ":id/password")
    @iOSFindBy(name = "password")
    private MobileElement passwordBox;

    @AndroidFindBy(id = ANDROID_APP_ID + ":id/sign_in_button")
    @iOSFindBy(name = "signIn")
    private MobileElement loginBtn;

    public MobileElement getEmailBox() {
        return emailBox;
    }

    public MobileElement getPasswordBox() {
        return passwordBox;
    }

    public MobileElement getLoginBtn() {
        return loginBtn;
    }

    public NextPage login(String username, String password) {
        logger.info("Username on qa:" + username);
        logger.info("Password on qa:" + password);

        emailBox.sendKeys(username);
        passwordBox.sendKeys(password);
        loginBtn.click();
        return Page.initialize(driver, NextPage.class);
    }

    @Override
    public MobileElement getPageIdentifier() {
        return loginBtn;
    }
}
