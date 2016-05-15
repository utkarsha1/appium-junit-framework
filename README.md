

###Cross-platform UI integration testing framework for mobile

This framework supports integration tests for native mobile apps. It is written using Java 8, JUnit 4.12 and appium-java-client. It's purpose is to write and run cross platform, end to end integration tests on native mobile apps. It is built using gradle (https://gradle.org/getting-started-gradle-java/). It optionally allows for integration with Sauce Labs/AWS Device Farm to run tests in the cloud or runs tests on local appium servers based on configuration specified.

#### Platforms: iOS and Android

* Sample LoginTest to demonstrate usage

#### Sample appium-local.properties

* For local development, the following properties must be defined in src/main/resources/appium-local.properties.
* File is added to .gitignore
* Start appium servers locally on ports defined in appium-local.properties below
   * appium -p 4750
   * appium -p 4752

````
    ios.server.port = 4750
    android.server.port = 4752

    ios.app = /file/system/location/for/ios/app/xyz.app
    android.app = /file/system/location/for/android/app/apk/app-debug.apk

    appium.version = 1.4.16 //latest version
````

#### Running your tests on local appium server:
* Clone the framework
  * git clone https://github.com/utkarsha1/appium-junit-framework.git
* The project is configured to use the Gradle Wrapper, you don’t need to download Gradle to start using it
* To build and unit test framework code:
   *  ./gradlew clean test
* Define appium-local.properties as above, else appium-local-default.properties will be used by framework
* Use the sample LoginTest as a template to write your first test
* To run integration tests from command line use custom gradle task defined
    * ./gradlew integrationTest : Run all tests against all platforms configured (currently android and iOS), default build from properties
    * ./gradlew integrationTest -PiOSOnly -PbuildNumber=25 : Run all tests only on iOS against specified build number
    * ./gradlew integrationTest -PandroidOnly -PbuildNumber=25: Run all tests only on android against specified build number
    * ./gradle -Dtest.single=LoginTest : run single test against both platforms

#### Running your tests on saucelabs
* Upload your app binary to saucelabs
    * https://wiki.saucelabs.com/display/DOCS/Uploading+Mobile+Applications+to+Sauce+Storage+for+Testing
* Update appium-saucelabs.properties, specify (See SaucelabsConfig.java, appium-saucelabs.properties for more details)
    * saucelabs.user.name = username
    * saucelabs.user.accessKey = key
    * saucelabs.ios.app = app_build_iosxxx.zip //xxx is a placeholder for build number for CI, this will allow testing different versions of the app
    * saucelabs.android.app = app_build_androidxxx.zip

#### Building app binaries for testing
   * iOS Simulator: https://developer.apple.com/library/watchos/documentation/DeveloperTools/Conceptual/testing_with_xcode/chapters/08-automation.html
   ````xcodebuild -sdk iphonesimulator -configuration Debug clean build````
     * This will create a binary file in ./build/Debug-iphonesimulator/AppName.app
   * Android Studio : Use Debug build or ````./gradlew build```` from command line
