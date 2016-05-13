package com.example.framework.appium.domain;

public enum Device {

    GOOGLE_NEXUS_6("Google Nexus 6 HD Emulator", "portrait"),
    GOOGLE_NEXUS_7("Google Nexus 7 HD Emulator", "portrait"),
    SAMSUNG_GALAXY_NOTE("Samsung Galaxy Note Emulator", "portrait"),
    SAMSUNG_GALAXY_NEXUS_EMULATOR("Samsung Galaxy Nexus Emulator", "portrait"),
    ANDROID_EMULATOR("Android Emulator", "portrait"),
    LG_NEXUS_4("LG Nexus 4 Emulator", "portrait"),
    HTC_ONE_X("HTC One X Emulator", "portrait"),
    MOTOROLA_DROID_RAZR("Motorola Droid Razr Emulator", "portrait"),
    SAMSUNG_GALAXY_S4("Samsung Galaxy S4 Emulator", "portrait"),
    SAMSUNG_GALAXY_S3("Samsung Galaxy S3 Emulator", "portrait"),
    IPHONE_6_PLUS("iPhone 6 Plus", "portrait"),
    IPHONE_6("iPhone 6", "portrait"),
    IPHONE_6S("iPhone 6s", "portrait"),
    IPHONE_5S("iPhone 5s", "portrait"),
    IPHONE_4S("iPhone 4s", "portrait"),
    IPHONE_5("iPhone 5", "portrait");

    private final String deviceName;
    private final String orientation;

    Device(String deviceName, String orientation) {
        this.deviceName = deviceName;
        this.orientation = orientation;
    }

    public String getName() {
        return deviceName;
    }

    public String getOrientation() {
        return orientation;
    }
}
