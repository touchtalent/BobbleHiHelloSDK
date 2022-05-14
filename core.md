# BobbleSDK

BobbleSDK is a collection of multiple SDKs which can be used to power conversations with cutting-edge AI technology. BobbleSDK is designed to provide tailored experience to suite requirements of every application. Developers can either choose to implement the whole IME inside their application or choose from a variety of modules individually as per their need.

## <a name="requirements"></a>System Requirements
- Minimum supported Android SDK level is 23 (Android 6.0)
- System architechture (ABIs) supported are [arm64-v8a, armeabi-v7a]

## BobbleIME 
Add a fully functional IME within your app with features such as global typing support, content sharing, emojis and BigMojis, keyboard themes, PopText, etc. The IME can be integrated within any application with very minimal efforts. Know more [here](readme_keyboard.md).

> Apart from the fully functional IME, this SDK also exposes APIs for individual modules (as mentioned below) which can be used to extend those functionalities for deep integration with your application.

## Individual Modules

- [Transliteration](readme_transliteration.md) - Accepts input in English script and transliterates them into native regional language. E.g - <i>"Namaste" -> "नमस्ते"</i>

- [Contextual Emoji Suggestion](readme_emoji.md) - Process an input text and get AI based contextual emoji suggestions. E.g - <i>"gussa mat dila" -> 😤,👿,😡,🙏,😠</i>
- [On-device Intent detection](readme_intent_detection.md) - Process an input text and derive user's intent out of it without user's data actually leaving user's device. E.g - <i>"naya mobile chaiye lekin paise nahi hai yaar" -> Intents - <b>Shopping</b>, <b>Loan</b></i>
- [Avatar, Stickers, Animated Stickers, Regional GIFs](readme_content.md) - Convert your user's selfies into fun personalised avatars <i>(Bobble Head)</i> which can be used independently as well as with large repository of personalised and expressive content formats(100K+).
- [Speech to Text](readme_speech_to_text.md) - Transcribe speech to text in 9 most used Indic langauges. 
- [Language Detection](bobble_language_detection.md) - Process an input text and detect language from it.


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

### <a name="size_chart"></a>SDK Size
Below is the size (approx) of the each module that would contribute to the APK size if integrated.

The sizes mentioned below are estimates and may vary based on different factors - Format of distribution (APK/AAB), device architecture used, 3rd party libraries used. Also, please note that Google Play Store compresses your actual APK size to 90% while delivering it to end user. The SDKs (except core) can also be used in dynamic modules to bypass extra APK size.

| Module                    | Size (in MBs) |
| --------------------------| ----------    |
| Core                      | 0.2           |
| Transliteration           | 1.8           |
| Contextual Emoji          | 0.2 + 1.8 (Shared across modules)          |
| Ondevice Intent Detection | 0.2 + 1.8 (Shared across modules)          |
| Avatar, Stickers, GIFs    | 2           | 
| Speech to text            | 0.2 + 1.8 (Shared across modules)          |