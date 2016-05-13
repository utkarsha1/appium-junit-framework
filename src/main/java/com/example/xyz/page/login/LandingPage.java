package com.example.xyz.page.login;

import com.example.framework.appium.domain.Environment;
import com.example.framework.appium.element.MobileElementBuilder;
import com.example.xyz.page.AppPage;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;

public class LandingPage extends AppPage {
    private static final MobileElementBuilder mobileElementBuilder = new MobileElementBuilder();

    @AndroidFindBy(id = ANDROID_APP_ID + ":id/sign_in")
    @iOSFindBy(name = "signIn")
    private MobileElement landingSignIn;

    public MobileElement getLandingSignIn() {
        return landingSignIn;
    }

    @Override
    protected MobileElement getPageIdentifier() {
        return landingSignIn;
    }
}
