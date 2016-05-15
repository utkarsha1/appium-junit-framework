

###Cross-platform UI integration testing framework for mobile

This framework supports integration tests for native mobile apps. It is written using Java 8, JUnit 4.12 and appium-java-client. It's purpose is to write and run cross platform, end to end integration tests on native mobile apps. It is built using gradle (https://gradle.org/getting-started-gradle-java/). It optionally allows for integration with Sauce Labs/AWS Device Farm to run tests in the cloud or runs tests on local appium servers based on configuration specified.

#### Platforms: iOS and Android

* Sample LoginTest to demonstrate usage

#### Sample appium-local.properties

* For local development, the following properties must be defined in src/main/resources/appium-local.properties.
* File is added to .gitignore
* Start appium servers locally on ports defined in appium-local.properties below````


    ios.server.port = 4750
    android.server.port = 4752

    ios.app = /location/for/ios/app/xyz.app
    android.app = /location/for/android/app/apk/app-debug.apk

    appium.version = 1.4.16 //latest version
