# README #

This is an Android library with integrated Firebase cloud messaging for handling notifications.

## Firebase set up

Go to [firebase console](https://console.firebase.google.com/) :
1. Create a new android app, fill all the fields like package name what supposed to be your application package, app name, etc. Then click "Register" button.
 2. Download file *google-services.json* and put it in the root directory of your application (app module). Click next.  
 3. Add gradle dependencies to main module and app module as the instruction says. Continue with the rest of steps to finish.
 4. Go to IDE where your project is open.
 5. Go to *gradle.properties* and add:
 `customerId = your_application_unique_identifier`
 It is needed for server to identify the client application and set as build config field in build.gradle file.

## Adding library to already existing Android app

 1. Get the *.aar* file and put it in your *app/libs* directory
 2. go to your gradle.build file in the app module and add next section in repositories: 
		`repositories {
             mavenCentral()
             flatDir {
                 dirs 'libs'
             }
         }` 
         
The above lines just find the *.aar* file in libs folder. 
3. Add in the dependency section the following line:
      ``implementation 'nl.worth.clangnotifications:clangnotifications-debug@aar'``

## Implementing library's features

**Firebase/Notifications feature**  
 - *New* -> *Service*, android studio will ask to provide a name and make sure you have unchecked boxes *Exported* and *Enabled*.
 - Open *AndroidManifest.xml*  there you should have a section < service > add the intent section inside service:
 
  `<service
        android:name=".MyAppFirebaseService"
 		android:exported="false">
 		<intent-filter>
 			<action android:name="com.google.firebase.MESSAGING_EVENT"/>
 		</intent-filter>
 </service>`
         
 - Go to your service class and remove everything it has inside(by default it inherits from *Service()* and overrides *onBind(intent: Intent)*).
 - To use library implementation make your Service class inherit from *ClangFirebaseMessagingService* it already has all the implementation from *FirebaseMessagingService* so there is no need to add anything else.

**Register account feature**

In order to register device just call an Instance of Clang with the function createAccount():
 Creating a new instance of Clang just requires a context from Activity or Service.
How to use:  

 * on a class level create field Clang:
       ``lateinit var clang: Clang``
 * inside *onCreate()* initialise it:
    	``clang = Clang.getInstance(this)``

***createAccount(successCallback: (CreateAccountResponse) -> Unit, errorCallback: (Throwable) -> Unit )***

Takes successCallback and errorCallback which are lambda functions. SuccessCallback accepts the returned result from the server and returns it in the CreateAccountResponse object. ErrorCallback accepts Throwable.
Behind the scene it send firebase token to the back end and from the response stores user id in shared preferences for future use purposes.
How to use(create field and initialise it as showed in first example):
		``clang.createAccount(  
          {  
          //do something with the result  
            },  
          {  
          //do something with error  
            }  
        )``
 
**Register event feature**

For sending/registering events on the server side *logEvent()* function is used. It accepts current context for 
accessing shared preferences in order to get user id from it,type of an event to register, map with key value pairs 
with all needed additional data and same as previous function success and error callbacks.
How to use(create field and initialise it as showed at the very beginning):
``clang.logEvent(this, "EVENT", mapOf("key1" to "value1", "key2" to "value2"),
                  {
                      //do something with the result 
                  },
                  {
                      //do something with error
                  }
              )``

### Links ###

* [Firebase project](https://console.firebase.google.com/project/test-a04ac/overview) (for access ask oboekesteijn@worth.systems or tpadalko@worth.systems)

