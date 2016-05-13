package com.example.xyz.page;

import com.example.framework.appium.page.Page;
import io.appium.java_client.MobileElement;

/**
 * Base page for app under test
 * @author utkarsha.padhye on 5/11/16
 */
public class AppPage extends Page {

    public static final String ANDROID_APP_ID = "com.example.android.xyz";

    @Override
    protected MobileElement getPageIdentifier() {
        return null;
    }
}
