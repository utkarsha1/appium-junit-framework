package com.example.framework.appium.domain;

public enum Environment {

    DEV("DEV", "Development"),
    QA("QA", "QA"),
    PROD("PROD", "Production");

    private final String androidEnv;
    private final String iOSEnv;

    Environment(String android, String iOS) {
        this.androidEnv = android;
        this.iOSEnv = iOS;
    }

    public String getAndroidEnv() {
        return androidEnv;
    }

    public String getiOSEnv() {
        return iOSEnv;
    }
}
