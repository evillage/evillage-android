# TeamITG - Android CanopyDeploy Library
This is the Android library project for the implementation of the CanopyDeploy platform on mobile platforms.
For the use of this in an example Android app with UI please see: https://github.com/evillage/evillage-android-demo

The library is written in Kotlin, uses Retrofit2 for HTTP calls and Firebase Cloud Messaging for notifications.

## CanopyDeploy library setup
To setup the CanopyDeploy library you need to call Clang.Companion.setUp() in your Application class and pass:
 * Base url (optional, defaults to https://api.clang.cloud)
 * Application context
 * Authentication token
 * Integration id 

The last three values will be provided when you contact e-Village to start integrating with the CanopyDeploy platform.

## CanopyDeploy library usage
To use the CanopyDeploy library you can just call Clang.Companion.getInstance() after Clang.Companion.setUp() and a Singleton instance of the CanopyDeploy library will be returned. 

## Publishing a new version of the library
In our test setup we have used https://jitpack.io to take care of builds of the library. By linking jitpack.io to the repository it can automatically create new builds based on version tags (tags on commits). Please refer to the documentation of jitpack.io on how to set this up.

## Using the CanopyDeploy library in your own project 
Just add the library to your project gradle

```
dependencies {
    implementation 'com.github.evillage:evillage-android:0.0.26'
}
```

## Generate documentation
To generate documentation for this project, follow these simple steps:

- In Android Studio terminal run ./gradlew dokka 
- The documentation will be generated into your project root directory under /documentation

## Links
* [Firebase project](https://console.firebase.google.com/project/test-a04ac/overview) (for access ask oboekesteijn@worth.systems or tpadalko@worth.systems)
* [Google doc with some edge cases, etc.](https://docs.google.com/document/d/1Nw7Ik1VY8Sz2PPtj86yaTUyZ9qnO__xaDHcRuk6Xsbk/edit?usp=sharing)
* [Token Server repo](https://github.com/evillage/evillage-token-server/src)
