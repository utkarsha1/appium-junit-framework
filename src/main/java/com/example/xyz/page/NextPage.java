package com.example.xyz.page;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;

/**
 * Sample page that should appear in app
 * after login success
 * @author utkarsha.padhye on 5/11/16
 */
public class NextPage extends AppPage {

    @AndroidFindBy(accessibility = "someAccessibilityIdentifier")
    @iOSFindBy(accessibility = "someAccessibilityIdentifier")
    private MobileElement someIdentifier;


    @Override
    protected MobileElement getPageIdentifier() {
        return someIdentifier;
    }
}
