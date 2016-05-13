package com.example.framework.appium.page;

import com.google.common.util.concurrent.Uninterruptibles;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public abstract class Page {

    private static final int MAX_TIME_OUT = 60;
    private static final int POLLING_INTERVAL = 10;

    protected MobileDriver driver;
    private FluentWait<MobileDriver> wait;

    public static <T extends Page> T initialize(MobileDriver driver, Class<T> tClass) {
        try {
            T page = tClass.newInstance();
            page.setDriver(driver);
            PageFactory.initElements(new AppiumFieldDecorator(driver), page);
            page.setWait(new FluentWait<>(driver)
                    .withTimeout(MAX_TIME_OUT, TimeUnit.SECONDS)
                    .pollingEvery(POLLING_INTERVAL, TimeUnit.SECONDS)
                    .ignoring(NoSuchElementException.class));
            if (page.getPageIdentifier() != null) {
                page.waitForElementVisible(page.getPageIdentifier());
            } else {
                Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            }
            return page;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setDriver(MobileDriver wd) {
        this.driver = wd;
    }

    public void setWait(FluentWait<MobileDriver> wait) {
        this.wait = wait;
    }

    protected abstract MobileElement getPageIdentifier();

    protected boolean isAndroid() {
        return (driver instanceof AndroidDriver);
    }

    protected boolean isIOS() {
        return (driver instanceof IOSDriver);
    }


    protected void waitForElementVisible(MobileElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementNotVisible(By by) {
       wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    protected void waitForElementText(MobileElement element, String text) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }
}
