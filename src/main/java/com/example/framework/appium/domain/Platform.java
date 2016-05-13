package com.example.framework.appium.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public enum Platform {

    ANDROID_5_1("Android","5.1"),
    ANDROID_5("Android", "5"),
    ANDROID_4_4("Android", "4.4"),
    ANDROID_4_3("Android", "4.3"),
    ANDROID_4_2("Android", "4.2"),
    ANDROID_4_1("Android","4.1"),
    ANDROID_4("Android","4.0"),
    IOS_8_4("iOS","8.4"),
    IOS_9("iOS", "9.0"),
    IOS_9_1("iOS", "9.1"),
    IOS_9_2("iOS", "9.2");

    private final String version;
    private final String name;

    Platform(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

}
