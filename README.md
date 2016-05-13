#Cross-platform Appium UI Tests

Integration tests for your native XYZ app

##iOS and Android

* LoginTest

## appium-local.properties

### For local development, the following properties must be defined in src/main/resources/appium-local.properties. File is added to .gitignore

    ios.server.port = 4725 
    android.server.port = 4726

    ios.app = /location/for/ios/app/xyz.app
    android.app = /location/for/android/app/apk/app-debug.apk

    appium.version = 1.4.16 //latest version
