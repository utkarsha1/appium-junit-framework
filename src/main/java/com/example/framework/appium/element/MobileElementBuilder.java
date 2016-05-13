package com.example.framework.appium.element;

import com.example.framework.appium.exception.AppiumException;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MobileElementBuilder {

    private final int timeOut = 30;
    private final int pollingInterval = 5;

    public MobileElement waitAndGetElement(final By locator, WebDriver wd, int timeOut, int pollingInterval) {
        Wait<WebDriver> wait = new FluentWait<>(wd)
                .withTimeout(timeOut, TimeUnit.SECONDS)
                .pollingEvery(pollingInterval, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        return wait.until(driver -> (MobileElement) driver.findElement(locator));
    }

    public MobileElement waitAndGetElement(final By locator, WebDriver wd) {
        return waitAndGetElement(locator, wd, timeOut, pollingInterval);
    }

    private List<MobileElement> waitAndGetElements(final By locator, WebDriver wd, int timeOut, int pollingInterval) {
        Wait<WebDriver> wait = new FluentWait<>(wd)
                .withTimeout(timeOut, TimeUnit.SECONDS)
                .pollingEvery(pollingInterval, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        List<WebElement> webElements = wait.until(driver -> driver.findElements(locator));

        return webElements.stream().map(webElement -> (MobileElement) webElement).collect(Collectors.toList());
    }

    public List<MobileElement> waitAndGetElements(final By locator, WebDriver wd) {
        return waitAndGetElements(locator, wd, timeOut, pollingInterval);
    }

    public MobileElement findElementByText(String text, MobileDriver driver) {
        if (driver instanceof IOSDriver) {
            return waitAndGetElement(MobileBy.AccessibilityId(text), driver);
        } else if (driver instanceof AndroidDriver) {
            return waitAndGetElement(MobileBy.AndroidUIAutomator(String.format("text(\"%s\")", text)), driver);
        } else {
            throw new AppiumException("Cannot find element, could not determine platform");
        }
    }
}
