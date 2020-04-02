# e-Village - Android Clang Library
This is the Android library project for the implementation of the Clang platform on mobile platforms.
For the use of this in an example Android app with UI please see: https://bitbucket.org/wi/evillage-android-demo

The library is written in Kotlin, uses Retrofit2 for HTTP calls and Firebase Cloud Messaging for notifications.

## Clang library setup
To use the Clang library you need to call Clang.getInstance() passing the Application context, the authorization token and the integration id  

The last two values will be provided when you contact e-Village to start integrating with the Clang platform.

## Publising a new version of the library
In our test setup we have used https://jitpack.io to take care of builds of the library. By linking jitpack.io to the repository it can automatically create new builds based on version tags (tags on commits). Please refer to the documentation of jitpack.io on how to set this up.

## Using the Clang library in your own project 
Just add the library to your project gradle

```
dependencies {
    implementation 'org.bitbucket.wi:evillage-android:0.0.3'
}
```

## Generate documentation
To generate documentation for this project, follow these simple steps:

- In Android Studio terminal run ./gradlew dokka 
- The documentation will be generated into your project root directory under /documentation

## Links
* [Firebase project](https://console.firebase.google.com/project/test-a04ac/overview) (for access ask oboekesteijn@worth.systems or tpadalko@worth.systems)
* [Google doc with some edge cases, etc.](https://docs.google.com/document/d/1Nw7Ik1VY8Sz2PPtj86yaTUyZ9qnO__xaDHcRuk6Xsbk/edit?usp=sharing)
* [Token Server repo](https://bitbucket.org/wi/evillage-token-server/src)
