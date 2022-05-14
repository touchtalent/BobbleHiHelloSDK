# BobbleSDK

BobbleSDK is a collection of multiple SDKs which can be used to power conversations with cutting-edge AI technology. BobbleSDK is designed to provide tailored experience to suite requirements of every application. Developers can either choose to implement the whole IME inside their application or choose from a variety of modules individually as per their need.

## <a name="requirements"></a>System Requirements
- Minimum supported Android SDK level is 23 (Android 6.0)
- System architechture (ABIs) supported are [arm64-v8a, armeabi-v7a]

## BobbleIME 
Add a fully functional IME within your app with features such as global typing support, content sharing, emojis and BigMojis, keyboard themes, PopText, etc. The IME can be integrated within any application with very minimal efforts.

> Apart from the fully functional IME, this SDK also exposes APIs for individual modules (as mentioned below) which can be used to extend those functionalities for deep integration with your application.

## Individual Modules

- [Transliteration](transliteration.md) - Accepts input in English script and transliterates them into native regional language. E.g - <i>"Namaste" -> "नमस्ते"</i>

- [Avatar, Stickers, Animated Stickers, Regional GIFs](content.md) - Convert your user's selfies into fun personalised avatars <i>(Bobble Head)</i> which can be used independently as well as with large repository of personalised and expressive content formats(100K+).


### <a name="setup"></a>Setting Up

- BobbleSDK uses BoM (Bill of Materials) to resolve versions of all modules by specifying a single version. Import the BoM for the BobbleSDK platform by adding following dependency in your application module’s build.gradle. 
```groovy
implementation platform('com.touchtalent.bobblesdk:bom:1.0.0')
```
- Import BobbleSDK Core which acts as base for all modules.
```groovy
implementation 'com.touchtalent.bobblesdk:core'
```
- Initialise BobbleSDK in your Application's ```onCreate()```
```java
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        BobbleSDK.initialise(this);
    }
}
```
### <a name="permissions"></a>Required Permissions

The SDK and modules uses few basic permissions, as listed below, for their smooth functioning. 

```java
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
Apart from the necessary permissions, the SDK recommends the client app to add following permissions and request it from their users for a customised experience.
```java
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.READ_CONTACTS" />
```

>P.S - Individual modules may require specific permissions which will be declared in their respective documentation.

