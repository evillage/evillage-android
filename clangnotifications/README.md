# README #

This is an Android library with integrated Firebase cloud messaging for handling notifications.

## Firebase set up

Go to [firebase console](https://console.firebase.google.com/) :
1. Create a new android app, fill all the fields like package name what supposed to be your application package, app name, etc. Then click "Register" button.
 2. Download file *google-services.json* and put it in the root directory of your application (app module). Click next.  
 3. Add gradle dependencies to main module and app module as the instruction says. Continue with the rest of steps to finish.
 4. Go to IDE where your project is open.
 5. Go to *gradle.properties* and add:
 `appId = your_application_unique_identifier`
 It is needed for server to identify the client application

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

There are two options available for registering an account: *with UI* and *without UI*. In both cases you just call an Instance of ClangNotifications with the method you need.
     
 ***createAccountWithUI(activity: Activity)*** 
 
Takes current context as argument and creates Intent to start newCreateAccountActivity. After finishing all the process goes back to the activity from which was started. 
How to use:  

 * on a class level create field ClangNotifications:
       ``lateinit var clangNotifications: ClangNotifications``
 * inside *onCreate()* initialise it:
    	``clangNotifications = ClangNotifications.getInstance(this)``
 * use the function:
    	``clangNotifications.createAccountWithUI(this)``

***createAccount(email: String, successCallback: (CreateAccountResponse) -> Unit, errorCallback: (Throwable) -> Unit )***

Takes user email(for registration) successCallback and errorCallback which are lambda functions. SuccessCallback accepts the returned result from the server and returns it in the CreateAccountResponse object. ErrorCallback accepts Throwable.

How to use(create field and initialise it as showed in first example):
		``clangNotifications.createAccount(email.text.toString(),  
          {  
          //do something with the result  
            },  
          {  
          //do something with error  
            }  
        )``
 

### Links ###

* [Firebase project](https://console.firebase.google.com/project/test-a04ac/overview) (for access ask oboekesteijn@worth.systems or tpadalko@worth.systems)

